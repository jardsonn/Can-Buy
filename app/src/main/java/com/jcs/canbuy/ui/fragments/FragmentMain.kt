package com.jcs.canbuy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcs.canbuy.CanBuyApplication
import com.jcs.canbuy.R
import com.jcs.canbuy.data.database.entities.ProductEntity
import com.jcs.canbuy.databinding.FragmentMainBinding
import com.jcs.canbuy.ui.adapter.MainAdapter
import com.jcs.canbuy.ui.viewmodes.MainViewModel
import com.jcs.canbuy.utils.CurrencyFormat

/**
 * Created by Jardson Costa on 02/11/2021.
 */


class FragmentMain : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((activity?.application as CanBuyApplication).repoProduct)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = binding.recyclerView
        val fab = binding.mainAddFab
        val bottomSheet = binding.sheet
        val tvBottomSheetTotal = bottomSheet.tvBottomSheetTotal
        val tvBottomSheetValueTotal = bottomSheet.tvBottomSheetValueTotal
        val tvBottomSheetTotalCart = bottomSheet.tvBottomSheetTotalCart
        val tvBottomSheetValueTotalCart = bottomSheet.tvBottomSheetValueTotalCart
        val adapter = MainAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.allProducts().observe(viewLifecycleOwner, {
            adapter.submitList(it)
            tvBottomSheetTotal.text = getString(R.string.total, it.size)
            tvBottomSheetValueTotal.text = CurrencyFormat.getValueFormated(viewModel.totalValue(it))
        })

        viewModel.productInCart().observe(viewLifecycleOwner, {
            tvBottomSheetTotalCart.text = getString(R.string.total_cart, it.size)
            tvBottomSheetValueTotalCart.text =
                CurrencyFormat.getValueFormated(viewModel.totalValue(it))
        })


        fab.setOnClickListener {
            viewModel.addProduct(ProductEntity(null, "Arroz", 8.9, 4, false))
        }

        adapter.setOnClickListener { product ->
            product.id?.let { id -> viewModel.deleteProduct(id) }
        }

        adapter.setOnCartListener { isChecked, product ->
            viewModel.updateProduct(
                productId = product.id!!,
                productName = product.name,
                productPrice = product.price,
                productQuantity = product.quantity,
                isChecked
            )
        }

        return root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}