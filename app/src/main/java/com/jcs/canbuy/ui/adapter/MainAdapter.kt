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
        private val productQuantity = binding.tvItemQuantity
        private val productValue = binding.tvItemValue
        private val btnDelete = binding.ibItemRemove
        private val productCartCheckbox = binding.cbItemCart

        fun bind(item: ProductEntity?) {
            item?.let { product ->
                productName.text = product.name
                productQuantity.text =
                    binding.root.context.getString(R.string.quantity_label, product.quantity)
                productValue.text = binding.root.context.getString(
                    R.string.value_label,
                    CurrencyFormat.getValueFormated(product.quantity.times(product.price))
                )
                btnDelete.setOnClickListener { listenerClickDelete?.onItemClick(product) }

                productCartCheckbox.isChecked = product.isInCart
                productCartCheckbox.setOnCheckedChangeListener { _, isChecked ->
                 listenerCart?.onItemCart(isChecked, product)
                }
            }

        }

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
                return oldItem.name == newItem.name
            }
        }
    }

    private fun setOnClickListener(listener: EventListClick.OnItemClickListener) {
       this.listenerClickDelete = listener
    }

    fun setOnClickListener(listener: (product: ProductEntity) -> Unit) {
       setOnClickListener(object : EventListClick.OnItemClickListener {
            override fun onItemClick(product: ProductEntity) {
                listener.invoke(product)
            }
        })
    }


    private fun setOnCartListener(listener: EventListClick.OnItemCartListener){
        this.listenerCart = listener
    }

    fun setOnCartListener(listener: (isChecked: Boolean, product: ProductEntity) -> Unit){
        setOnCartListener(object : EventListClick.OnItemCartListener{
            override fun onItemCart(isChecked: Boolean, product: ProductEntity) {
               listener.invoke(isChecked, product)
            }
        })
    }

}