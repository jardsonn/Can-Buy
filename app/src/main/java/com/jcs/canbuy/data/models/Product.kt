package com.jcs.canbuy.data.models

/**
 * Created by Jardson Costa on 03/11/2021.
 */

data class Product(
    val id: Int? = null,
    val name: String,
    val price: Double,
    val quantity: Int
)