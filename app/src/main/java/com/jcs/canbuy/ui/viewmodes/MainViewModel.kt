package com.jcs.canbuy.ui.viewmodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jcs.canbuy.data.models.Product
import com.jcs.canbuy.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * Created by Jardson Costa on 03/11/2021.
 */

class MainViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _actionState = MutableStateFlow<ActionState>(ActionState.Added)
    val actionState: StateFlow<ActionState> get() = _actionState

    fun getAllProducts(productList: (LiveData<List<Product>>) -> Unit) {
        viewModelScope.launch {
            productList.invoke(
                repository.getAllProducts().flowOn(Dispatchers.Main)
                    .asLiveData(context = viewModelScope.coroutineContext)
            )
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            val success = repository.insertProduct(product)
            _actionState.value = if (success) ActionState.Added else ActionState.Error
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            val success = repository.deleteProduct(productId)
            _actionState.value = if (success) ActionState.Deleted else ActionState.Error
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch {
            val success = repository.deleteAllProducts()
            _actionState.value = if (success) ActionState.Deleted else ActionState.Error
        }
    }

    fun updateProduct(
        productId: Int,
        productName: String,
        productPrice: Double,
        productQuantity: Int
    ) {
        viewModelScope.launch {
            val success =
                repository.updateProduct(productId, productName, productPrice, productQuantity)
            _actionState.value = if (success) ActionState.Updated else ActionState.Error
        }
    }


    sealed class ActionState {
        object Added : ActionState()
        object Deleted : ActionState()
        object Updated : ActionState()
        object Error : ActionState()
    }

}