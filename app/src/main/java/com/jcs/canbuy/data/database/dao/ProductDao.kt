package com.jcs.canbuy.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jcs.canbuy.data.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

//import kotlinx.coroutines.flow.Flow

/**
 * Created by Jardson Costa on 03/11/2021.
 */

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productEntity: ProductEntity)

    @Delete
    suspend fun delete(productEntity: ProductEntity)

    @Update
    suspend fun update(productEntity: ProductEntity)

    @Query("UPDATE product_table SET is_in_cart = :isInCart WHERE id = :productId")
    suspend fun updeteCart(productId: Int, isInCart: Boolean)

    @Query("SELECT * FROM product_table WHERE id = :id")
    fun getProductById(id: Int): Flow<ProductEntity>

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM product_table")
     fun getQuantity(): Flow<Int>

     @Query("SELECT * FROM product_table WHERE is_in_cart = 1")
     fun getProductInCart(): Flow<List<ProductEntity>>
}