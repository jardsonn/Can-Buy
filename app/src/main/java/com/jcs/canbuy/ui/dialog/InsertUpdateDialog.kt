package com.jcs.canbuy.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.jcs.canbuy.R
import com.jcs.canbuy.data.database.entities.ProductEntity
import com.jcs.canbuy.databinding.DialogAddBinding
import com.jcs.canbuy.utils.StringUtils

/**
 * Created by Jardson Costa on 11/11/2021.
 */

class InsertUpdateDialog : DialogFragment() {

    private var productEntity: ProductEntity? = null
    private var positiveClickListener: OnDialogButtonsListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val alertDialog = AlertDialog.Builder(it)
            val binding = DialogAddBinding.inflate(LayoutInflater.from(this.context))
            alertDialog.setView(binding.root)
            initDialog(binding)
            val dialog = alertDialog.create()
            dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog
        } ?: throw IllegalStateException("This activity is null")
    }

    private fun initDialog(binding: DialogAddBinding) = binding.apply {
        val titleDialog = binding.tvDialogTitle
        val editName = binding.editNameDialog
        val editQuantity = binding.editQuantityDialog
        val editPrice = binding.editPriceDialog
        val textUnit = binding.tvAutoCompleteUnit
        val positiveBtn = binding.btnConfirmDialog
        val cancelBtn = binding.btnCancelDialog
        val cartCb = binding.cbDialogCart
        val unitArray = activity?.let { resources.getStringArray(R.array.unit_of_measurement) }
        val itemUnitAdapter =
            ArrayAdapter(
                this.root.context,
                R.layout.support_simple_spinner_dropdown_item,
                unitArray ?: arrayOf()
            )

        editName.doOnTextChanged { text, _, _, _ ->
            positiveBtn.isEnabled = text.let { !it.isNullOrBlank() }
        }

        productEntity?.let {
            titleDialog.text = getString(R.string.update_product)

            editName.setText(it.name)
            editPrice.setText(it.price.toString())
            editQuantity.setText(it.quantity.toString())
            textUnit.setText(it.unit)
            cartCb.isChecked = it.isInCart
        }

        textUnit.setAdapter(itemUnitAdapter)

        positiveBtn.setOnClickListener {
            positiveClickListener?.onClick(
                ProductEntity(
                    id = productEntity?.id,
                    name = StringUtils.editToString(editName).trim(),
                    price = StringUtils.editToDouble(editPrice),
                    quantity = StringUtils.editToDouble(editQuantity),
                    unit = StringUtils.getUnit(textUnit),
                    isInCart = cartCb.isChecked
                )
            )
            dialog?.dismiss()
        }

        cancelBtn.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun setProductEntity(productEntity: ProductEntity?) {
        this.productEntity = productEntity
    }

    private fun setOnClickListener(listener: OnDialogButtonsListener) {
        this.positiveClickListener = listener
    }

    fun setOnClickListener(l: (product: ProductEntity) -> Unit) {
        setOnClickListener(object : OnDialogButtonsListener {
            override fun onClick(product: ProductEntity) {
                l.invoke(product)
            }
        })
    }

    companion object {
        fun getInstance(productEntity: ProductEntity?) = InsertUpdateDialog().apply {
            setProductEntity(productEntity)
        }
    }
}