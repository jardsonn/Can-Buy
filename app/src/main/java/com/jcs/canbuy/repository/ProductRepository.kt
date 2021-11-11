package com.jcs.canbuy.repository

import com.jcs.canbuy.data.database.dao.ProductDao
import com.jcs.canbuy.data.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

/**
 * Created by Jardson Costa on 03/11/2021.
 */

class ProductRepository(private val dao: ProductDao) {

    val allProducts: Flow<List<ProductEntity>> = dao.getAllProducts()

    val productQuantity: Flow<Int> = dao.getQuantity()

    val productInCart: Flow<List<ProductEntity>> = dao.getProductInCart()

    fun getProductById(productId: Int): Flow<ProductEntity> = dao.getProductById(productId)

    suspend fun insertProductInCart(productId: Int, isInCart: Boolean): Boolean{
        return try {
            dao.updeteCart(productId, isInCart)
            true
        }catch (e: java.lang.Exception){
            false
        }
    }

    suspend fun insertProduct(product: ProductEntity): Boolean {
        return try {
            dao.insert(product)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteProduct(productId: Int): Boolean {
        return try {
            dao.getProductById(productId).collect {
                dao.delete(it)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteAllProducts(): Boolean {
        var hasDeleted = false
        dao.getAllProducts().collect {
            hasDeleted = if (it.isNotEmpty()) try {
                dao.deleteAll()
                true
            } catch (e: Exception) {
                false
            } else false
        }
        return hasDeleted
    }

    suspend fun updateProduct(product: ProductEntity):Boolean{
       return try {
           dao.update(product)
           true
       }catch (e: Exception){
           false
       }
    }

    suspend fun updateProduct(
        productId: Int,
        productName: String,
        productPrice: Double,
        productQuantity: Double,
        unit: String,
        isInCart: Boolean
    ): Boolean {
        return try {
            dao.update(
                ProductEntity(
                    productId,
                    productName,
                    productPrice,
                    productQuantity,
                    unit,
                    isInCart
                )
            )
            true
        } catch (e: Exception) {
            false
        }
    }

}

