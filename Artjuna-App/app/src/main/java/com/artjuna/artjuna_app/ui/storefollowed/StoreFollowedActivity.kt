package com.artjuna.artjuna_app.ui.storefollowed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.artjuna.artjuna_app.R
import com.artjuna.artjuna_app.databinding.ActivityStoreFollowedBinding
import com.artjuna.artjuna_app.ui.storefollowed.adapter.StoreAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoreFollowedActivity : AppCompatActivity() {

    private lateinit var binding:ActivityStoreFollowedBinding

    private val viewModel:StoreFollowedViewModel by viewModel()
    private val storeAdapter = StoreAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreFollowedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        getListStore()
    }

    private fun getListStore() {
        viewModel.getAllStoreFollowed().observe(this){
            storeAdapter.submitList(it)
        }
    }

    private fun setupAdapter() {
        binding.rvStore.adapter = storeAdapter
    }
}