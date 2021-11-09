package com.jcs.canbuy.utils

import java.text.NumberFormat
import java.util.*

/**
 * Created by Jardson Costa on 08/11/2021.
 */

class CurrencyFormat {

   companion object{
       fun getValueFormated(value: Any): String{
           return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(value)
       }
   }
}