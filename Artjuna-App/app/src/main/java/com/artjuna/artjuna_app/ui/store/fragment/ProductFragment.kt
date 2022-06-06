package com.artjuna.artjuna_app.ui.store.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.FragmentProductBinding
import com.artjuna.artjuna_app.ui.adapter.ProductAdapter
import com.artjuna.artjuna_app.ui.store.StoreViewModel
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductFragment : Fragment() {

    private val viewModel: StoreViewModel by viewModel()
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
        getStoreId()
    }

    private fun getStoreId() {
        val storeId = arguments?.getString(STORE_ID)
        getProductByUserId(storeId!!)
    }

    private fun getProductByUserId(storeId: String) {
        viewModel.getProductByUserId(storeId).observe(viewLifecycleOwner){
            when(it){
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    productAdapter.submitList(it.data)
                    showLoading(false)
                }
                is Result.Error -> {
                    AppUtils.showToast(requireContext(), it.error)
                    showLoading(false)
                }
            }
        }
    }

    private fun setupAdapter(){
        binding.rvProduct.adapter = productAdapter
    }

    private fun showLoading(loading:Boolean){
        with(binding){
            load.visibility = if(loading) View.VISIBLE else View.GONE
            rvProduct.visibility = if(loading) View.GONE else View.VISIBLE
        }
    }

    companion object{
        const val STORE_ID = "STORE_ID"
    }

}