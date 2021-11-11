package com.jcs.canbuy.ui.dialog

import com.jcs.canbuy.data.database.entities.ProductEntity

/**
 * Created by Jardson Costa on 09/11/2021.
 */

interface OnDialogButtonsListener {
    fun onClick(product: ProductEntity)
}