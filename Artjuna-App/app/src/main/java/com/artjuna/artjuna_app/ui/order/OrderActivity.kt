package com.artjuna.artjuna_app.ui.order

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityOrderBinding
import com.artjuna.artjuna_app.ui.order.adapter.OrderAdapter
import com.artjuna.artjuna_app.ui.order.adapter.OrderHistoryAdapter
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOrderBinding

    private val viewModel:OrderViewModel by viewModel()
    private val orderAdapter = OrderAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        getListOrder()
    }

    private fun getListOrder() {
        viewModel.getOrderHistory().observe(this){
            when(it){
                is Result.Loading -> showEmpty(false)
                is Result.Error -> {
                    AppUtils.showToast(this, it.error)
                    showEmpty(false)
                }
                is Result.Success -> {
                    orderAdapter.submitList(it.data)
                    showEmpty(it.data.isEmpty(), "You haven't placed an order yet")
                }
            }
        }
    }

    private fun showEmpty(empty: Boolean, message:String="") {
        binding.empty.root.visibility = if(empty) View.VISIBLE else View.GONE
        binding.empty.tvMessage.text = message
    }

    private fun setupAdapter() {
        binding.rvOrder.adapter = orderAdapter
    }
}