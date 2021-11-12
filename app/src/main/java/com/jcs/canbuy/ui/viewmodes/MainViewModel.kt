package com.jcs.canbuy.ui.viewmodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.jcs.canbuy.data.database.entities.ProductEntity
import com.jcs.canbuy.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Jardson Costa on 03/11/2021.
 */

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    fun allProducts(): LiveData<List<ProductEntity>> =
        repository.allProducts.asLiveData(Dispatchers.IO)

    fun productInCart(): LiveData<List<ProductEntity>> =
        repository.productInCart.asLiveData(Dispatchers.IO)

    fun productQuantity(): LiveData<Int> =
        repository.productQuantity.asLiveData(Dispatchers.IO)

    fun getProductById(productId: Int?): LiveData<ProductEntity> =
        repository.getProductById(productId!!).asLiveData(Dispatchers.IO)

    fun totalValue(list: List<ProductEntity>): Double {
        var totalPrice = 0.0
        for (product in list) {
            totalPrice += product.price * product.quantity
        }
        return totalPrice
    }

    fun deleteProduct(productId: Int?) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val success = repository.deleteProduct(productId!!)
            }
        }
    }

    fun deleteAllProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val success = repository.deleteAllProducts()
        }
    }

    fun addProduct(product: ProductEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val success = repository.insertProduct(product)
        }
    }

    fun addProductInCart(productId: Int, isInCart: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val success = repository.insertProductInCart(productId, isInCart)
        }
    }

    fun updateProduct(product: ProductEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val success = repository.updateProduct(
                productId = product.id!!,
                productName = product.name,
                productPrice = product.price,
                productQuantity = product.quantity,
                unit = product.unit,
                isInCart = product.isInCart,
            )
        }
    }

    fun updateProduct(
        productId: Int,
        productName: String,
        productPrice: Double,
        productQuantity: Double,
        productUnit: String,
        isInCart: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val success =
                repository.updateProduct(
                    productId,
                    productName,
                    productPrice,
                    productQuantity,
                    productUnit,
                    isInCart
                )
        }
    }

}