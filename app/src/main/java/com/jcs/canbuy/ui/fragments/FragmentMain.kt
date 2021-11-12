package com.jcs.canbuy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcs.canbuy.R
import com.jcs.canbuy.data.database.entities.ProductEntity
import com.jcs.canbuy.databinding.FragmentMainBinding
import com.jcs.canbuy.ui.adapter.MainAdapter
import com.jcs.canbuy.ui.dialog.InsertUpdateDialog
import com.jcs.canbuy.ui.viewmodes.MainViewModel
import com.jcs.canbuy.utils.CurrencyFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

/**
 * Created by Jardson Costa on 02/11/2021.
 */

@AndroidEntryPoint
class FragmentMain : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

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
            tvBottomSheetTotal.text = getString(R.string.total, it.size.toString())
            tvBottomSheetValueTotal.text = CurrencyFormat.getValueFormated(viewModel.totalValue(it))
        })

        viewModel.productInCart().observe(viewLifecycleOwner, {
            tvBottomSheetTotalCart.text = getString(R.string.total_cart, it.size.toString())
            tvBottomSheetValueTotalCart.text =
                CurrencyFormat.getValueFormated(viewModel.totalValue(it))
        })

        fab.setOnClickListener { _ ->
            InsertUpdateDialog.getInstance(null).apply {
                setOnClickListener { viewModel.addProduct(it) }
                show(this@FragmentMain.parentFragmentManager, "dialog_insert")
            }
        }

        adapter.setOnDeleteClickListener { product ->
            viewModel.deleteProduct(product.id)
        }

        adapter.setOnCartListener { isChecked, productId ->
            viewModel.addProductInCart(productId, isChecked)
        }

        adapter.setOnItemClickListener { product ->
            InsertUpdateDialog.getInstance(product).apply {
                setOnClickListener { viewModel.updateProduct(it) }
                show(this@FragmentMain.parentFragmentManager, "dialog_update")
            }
        }

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}