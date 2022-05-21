package com.artjuna.artjuna_app.ui.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.FragmentProductBinding
import com.artjuna.artjuna_app.ui.adapter.ProductAdapter
import com.artjuna.artjuna_app.ui.store.StoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductFragment : Fragment() {

    private val storeViewModel: StoreViewModel by viewModel()
    private lateinit var binding:FragmentProductBinding
    private val productAdapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setData()
    }
    private fun setupAdapter(){
        binding.rvProduct.adapter = productAdapter
    }
    private fun setData(){
        storeViewModel.getProduct().observe(viewLifecycleOwner){
            when(it){
                is Result.Success -> productAdapter.submitList(it.data)
            }
        }
    }

}