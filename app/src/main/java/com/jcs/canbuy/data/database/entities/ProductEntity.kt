package com.jcs.canbuy.data.database.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.jcs.canbuy.data.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

/**
 * Created by Jardson Costa on 03/11/2021.
 */

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val price: Double,
    val quantity: Int
) {
    @Ignore
    val isInCart: Boolean = false
}

fun toProduct(model: ProductEntity): Product = Product(
    model.id,
    model.name,
    model.price,
    model.quantity
)

fun toEntity(model: Product): ProductEntity = ProductEntity(
    model.id,
    model.name,
    model.price,
    model.quantity
)

fun toEntityList(productList: List<Product>): Flow<List<ProductEntity>> {
    val listModel = ArrayList<ProductEntity>()
    for (product in productList) {
        val productConverter = ProductEntity(
            product.id,
            product.name,
            product.price,
            product.quantity
        )
        listModel.add(productConverter)
    }
    return flowOf(listModel)
}

suspend fun toProductList(productList: Flow<List<ProductEntity>>): Flow<List<Product>> {
    val listProduct = ArrayList<Product>()
    for (entities in productList.toList()){
        for (product in entities) {
            listProduct.add(toProduct(product))
        }
    }
    return flowOf(listProduct)
}
