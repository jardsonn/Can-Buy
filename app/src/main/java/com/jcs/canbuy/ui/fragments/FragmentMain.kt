package com.jcs.canbuy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jcs.canbuy.databinding.FragmentMainBinding

/**
 * Created by Jardson Costa on 02/11/2021.
 */

class FragmentMain: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}