package com.jcs.canbuy.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by Jardson Costa on 08/11/2021.
 */

class CurrencyFormat {

    companion object {
        fun getValueFormated(value: Any): String {
            val currencySymbol =
                (NumberFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat).decimalFormatSymbols.currencySymbol
            return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(value)
                .replace(currencySymbol, "$currencySymbol ")
        }

    }
}