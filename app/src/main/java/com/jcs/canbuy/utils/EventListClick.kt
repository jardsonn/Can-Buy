package com.jcs.canbuy.utils

import com.jcs.canbuy.data.database.entities.ProductEntity

/**
 * Created by Jardson Costa on 08/11/2021.
 */

class EventListClick {

    interface OnItemClickListener {
        fun onItemClick(product: ProductEntity)
    }

    interface OnItemCartListener{
        fun onItemCart(isChecked: Boolean, product: ProductEntity)
    }
}