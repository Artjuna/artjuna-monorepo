package com.artjuna.artjuna_app.ui.cart

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivityCartBinding
import com.artjuna.artjuna_app.ui.adapter.ProductAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCartBinding
    private val viewModel:CartlViewModel by viewModel()
    private val productAdapter = ProductAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonClick()
        setupAdapter()
        getListProductInCart()
    }

    private fun setButtonClick() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun getListProductInCart() {
        viewModel.getAllProductInCart().observe(this){
            productAdapter.submitList(it)
            showEmpty(it.isEmpty(), "You have no product in cart")
        }
    }

    private fun showEmpty(empty: Boolean, message:String="") {
        binding.empty.root.visibility = if(empty) View.VISIBLE else View.GONE
        binding.empty.tvMessage.text = message
    }


    private fun setupAdapter() {
        binding.rvProductCart.adapter = productAdapter
    }

    override fun onResume() {
        super.onResume()
        getListProductInCart()
    }
}