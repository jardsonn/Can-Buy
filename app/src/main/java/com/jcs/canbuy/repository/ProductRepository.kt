package com.jcs.canbuy.repository

import com.jcs.canbuy.data.database.dao.ProductDao
import com.jcs.canbuy.data.database.entities.*
import com.jcs.canbuy.data.models.Product
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

/**
 * Created by Jardson Costa on 03/11/2021.
 */

class ProductRepository(private val dao: ProductDao) {


     suspend fun getAllProducts(): Flow<List<Product>>{
        return toProductList(dao.getAllProducts())
    }


    suspend fun insertProduct(product: Product): Boolean {
        return try {
            dao.insert(toEntity(product))
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
        var hasDeleted: Boolean = false
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

    suspend fun updateProduct(
        productId: Int,
        productName: String,
        productPrice: Double,
        productQuantity: Int
    ): Boolean {
        return try {
            dao.update(
                toEntity(
                    Product(
                        productId,
                        productName,
                        productPrice,
                        productQuantity
                    )
                )
            )
            true
        } catch (e: Exception) {
            false
        }
    }

}

