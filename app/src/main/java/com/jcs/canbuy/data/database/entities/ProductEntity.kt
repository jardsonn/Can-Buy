package com.jcs.canbuy.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.math.BigDecimal

/**
 * Created by Jardson Costa on 03/11/2021.
 */

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val price: Double,
    val quantity: Double,
    val unit: String,
    @ColumnInfo(name = "is_in_cart")
    val isInCart: Boolean
)
