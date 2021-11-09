package com.jcs.canbuy.ui.viewmodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.jcs.canbuy.data.database.entities.ProductEntity
import com.jcs.canbuy.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by Jardson Costa on 03/11/2021.
 */

class MainViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _actionState = MutableStateFlow<ActionState>(ActionState.Added)
    val actionState: StateFlow<ActionState> get() = _actionState

    fun allProducts(): LiveData<List<ProductEntity>> =
        repository.allProducts.asLiveData(Dispatchers.IO)

    fun productInCart(): LiveData<List<ProductEntity>> =
        repository.productInCart.asLiveData(Dispatchers.IO)

    fun productQuantity(): LiveData<Int> =
        repository.productQuantity.asLiveData(Dispatchers.IO)

    fun totalValue(list: List<ProductEntity>): Double{
        var totalPrice = 0.0
        for (product in list){
            totalPrice += product.price * product.quantity
        }
        return totalPrice
    }

    fun addProduct(product: ProductEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val success = repository.insertProduct(product)
            _actionState.value = if (success) ActionState.Added else ActionState.Error
        }
    }

    fun deleteProduct(productId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val success = repository.deleteProduct(productId)
            _actionState.value = if (success) ActionState.Deleted else ActionState.Error
        }
    }

    fun deleteAllProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val success = repository.deleteAllProducts()
            _actionState.value = if (success) ActionState.Deleted else ActionState.Error
        }
    }

    fun updateProduct(
        productId: Int,
        productName: String,
        productPrice: Double,
        productQuantity: Int,
        isInCart: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val success =
                repository.updateProduct(productId, productName, productPrice, productQuantity, isInCart)
            _actionState.value = if (success) ActionState.Updated else ActionState.Error
        }
    }


    sealed class ActionState {
        object Added : ActionState()
        object Deleted : ActionState()
        object Updated : ActionState()
        object Error : ActionState()
    }

    class MainViewModelFactory(private val repository: ProductRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }
}