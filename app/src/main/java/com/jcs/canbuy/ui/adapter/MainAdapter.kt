package com.jcs.canbuy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jcs.canbuy.R
import com.jcs.canbuy.data.database.entities.ProductEntity
import com.jcs.canbuy.databinding.ItemPurchasesBinding
import com.jcs.canbuy.utils.CurrencyFormat
import com.jcs.canbuy.utils.EventListClick

/**
 * Created by Jardson Costa on 05/11/2021.
 */

class MainAdapter : ListAdapter<ProductEntity, MainAdapter.MainViewHolder>(MAIN_COMPARATOR) {

    private var listenerClickDelete: EventListClick.OnItemClickListener? = null
    private var listenerCart: EventListClick.OnItemCartListener? = null
    private var listenerItem: EventListClick.OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemPurchasesBinding.inflate(inflate)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    inner class MainViewHolder(private val binding: ItemPurchasesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val productName = binding.tvItemName
        private val productQuantityValue = binding.tvItemQuantityValue

        private val btnDelete = binding.ibItemRemove
        private val productCartCheckbox = binding.cbItemCart

        private val itemContainer = binding.clItemContainer

        fun bind(item: ProductEntity?) {
            item?.let { product ->

                productName.text = product.name
                productQuantityValue.text = binding.root.context.getString(
                    R.string.total_value,
                    if ((product.quantity % 1) == 0.0) product.quantity.toInt()
                        .toString() else product.quantity.toString(),
                    product.unit,
                    CurrencyFormat.getValueFormated(product.price * product.quantity)
                )
                btnDelete.setOnClickListener { listenerClickDelete?.onItemClick(product) }

                productCartCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    listenerCart?.onItemCart(isChecked, product.id)
                }

                itemContainer.setOnClickListener { listenerItem?.onItemClick(product) }
                productCartCheckbox.isChecked = product.isInCart
            }

        }

    }

    private fun setOnDeleteClickListener(listener: EventListClick.OnItemClickListener) {
        this.listenerClickDelete = listener
    }

    fun setOnDeleteClickListener(listener: (product: ProductEntity) -> Unit) {
        setOnDeleteClickListener(object : EventListClick.OnItemClickListener {
            override fun onItemClick(product: ProductEntity) {
                listener.invoke(product)
            }
        })
    }


    private fun setOnCartListener(listener: EventListClick.OnItemCartListener) {
        this.listenerCart = listener
    }

    fun setOnCartListener(listener: (isChecked: Boolean, productId: Int) -> Unit) {
        setOnCartListener(object : EventListClick.OnItemCartListener {
            override fun onItemCart(isChecked: Boolean, productId: Int?) {
                listener.invoke(isChecked, productId!!)
            }
        })
    }

    private fun setOnItemClickListener(listener: EventListClick.OnItemClickListener) {
        this.listenerItem = listener
    }

    fun setOnItemClickListener(lisnter: (product: ProductEntity) -> Unit) {
        setOnItemClickListener(object : EventListClick.OnItemClickListener {
            override fun onItemClick(product: ProductEntity) {
                lisnter.invoke(product)
            }

        })
    }

    companion object {
        private val MAIN_COMPARATOR = object : DiffUtil.ItemCallback<ProductEntity>() {
            override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductEntity,
                newItem: ProductEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}