package com.jcs.canbuy.utils

import android.widget.EditText
import java.util.*

/**
 * Created by Jardson Costa on 09/11/2021.
 */

class StringUtils {

    companion object{
        fun editToString(edit: EditText): String{
            return edit.text.toString()
        }

        fun editToDouble(edit: EditText): Double{
            return try {
                edit.text.toString().trim().toDouble()
            }catch (e: Exception){
                0.0
            }

        }

        fun getUnit(edit: EditText):String{
           var unit = edit.text.toString().lowercase(Locale.getDefault())
            if (unit == "l") unit = unit.uppercase()
            return if (unit.isNotEmpty()) unit else "un"
        }
    }
}