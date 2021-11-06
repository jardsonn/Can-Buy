package com.jcs.canbuy.data.database.dao

import androidx.room.*
import com.jcs.canbuy.data.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM product_table WHERE id = :id")
     fun getProductById(id:Int): Flow<ProductEntity>

    @Query("SELECT * FROM product_table")
     fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()

}