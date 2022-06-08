package com.artjuna.artjuna_app.ui.mystore.activity.orderhistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityOrderHistoryBinding
import com.artjuna.artjuna_app.ui.order.adapter.OrderHistoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOrderHistoryBinding

    private val viewModel:OrderHistoryViewModel by viewModel()
    private val orderHistoryAdapter = OrderHistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        getOrderHistory()
    }

    private fun getOrderHistory() {
        viewModel.getOrderHistory().observe(this){
            when(it){
                is Result.Success -> orderHistoryAdapter.submitList(it.data)
            }
        }
    }

    private fun setupAdapter() {
        binding.rvOrder.adapter = orderHistoryAdapter
    }
}