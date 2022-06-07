package com.artjuna.artjuna_app.ui.store.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.FragmentPostBinding
import com.artjuna.artjuna_app.ui.adapter.PostAdapter
import com.artjuna.artjuna_app.ui.store.StoreViewModel
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostFragment : Fragment() {


    private val viewModel:StoreViewModel by viewModel()
    private lateinit var binding: FragmentPostBinding
    private val postAdapter = PostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        getStoreId()
    }

    private fun getStoreId() {
        val storeId = arguments?.getString(STORE_ID)
        getListPost(storeId!!)
    }

    private fun getListPost(storeId: String) {
        viewModel.getPostByUserId(storeId).observe(viewLifecycleOwner){
            when(it){
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    AppUtils.showToast(requireContext(), it.error)
                }
                is Result.Success -> {
                    showLoading(false)
                    postAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun showLoading(isLoading:Boolean){
        with(binding){
            postLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
            rvPost.visibility = if(isLoading) View.GONE else View.VISIBLE
        }
    }


    private fun setupAdapter(){
        binding.rvPost.adapter = postAdapter
    }

    companion object{
        const val STORE_ID = "STORE_ID"
    }



}