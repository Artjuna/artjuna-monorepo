package com.artjuna.artjuna_app.ui.mystore.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.FragmentMyProductBinding
import com.artjuna.artjuna_app.ui.mystore.activity.addproduct.AddProductActivity
import com.artjuna.artjuna_app.ui.mystore.MyStoreViewModel
import com.artjuna.artjuna_app.ui.mystore.adapter.MyProductAdapter
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyProductFragment : Fragment() {

    private lateinit var binding:FragmentMyProductBinding

    private val viewModel:MyStoreViewModel by viewModel()
    private val myProductAdapter = MyProductAdapter()
    private var userId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateView()
        setupAdapter()
        getUserId()
    }

    private fun getUserId() {
        userId = arguments?.getString(MyPostFragment.USER_ID)!!
        getListProduct()
    }

    private fun getListProduct() {
        viewModel.getProductByUserId(userId).observe(viewLifecycleOwner){
            when(it){
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    AppUtils.showToast(requireContext(), it.error)
                }
                is Result.Success -> {
                    showLoading(false)
                    myProductAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun showLoading(loading:Boolean){
        with(binding){
            load.visibility = if(loading) View.VISIBLE else View.GONE
            rvProduct.visibility = if(loading) View.GONE else View.VISIBLE
        }
    }

    private fun setupAdapter(){
        binding.rvProduct.adapter = myProductAdapter
    }

    private fun populateView() {
        binding.btnAddProduct.setOnClickListener {
            startActivity(Intent(requireContext(), AddProductActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getListProduct()
    }

    companion object{
        const val USER_ID = "USER_ID"
    }

}