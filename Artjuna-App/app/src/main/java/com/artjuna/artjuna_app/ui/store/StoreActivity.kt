package com.artjuna.artjuna_app.ui.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivityStoreBinding
import com.artjuna.artjuna_app.ui.store.adapter.StorePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoreActivity : AppCompatActivity() {

    private val storeViewModel:StoreViewModel by viewModel()
    private lateinit var binding:ActivityStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateView()
        showTabLayout()
    }

    private fun populateView() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun showTabLayout() {
        val storePagerAdapter = StorePagerAdapter(this)
        with(binding){
            viewPager.adapter = storePagerAdapter
            TabLayoutMediator(tabs, viewPager){tab, position ->
                tab.text = TAB_TITLES[position]
            }.attach()
        }
    }

    companion object{
        private val TAB_TITLES = arrayListOf(
            "Product",
            "Post"
        )
    }
}