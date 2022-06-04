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

        setButtonClick()
        getStoreData()
        showTabLayout()
    }

    private fun getStoreData() {
        if(intent.extras != null){
            val storeId = intent.extras!!.getString(EXTRA_STORE_ID)
            val storeCity = intent.extras!!.getString(EXTRA_STORE_CITY)
            populateView(storeId,storeCity)
        }
    }

    private fun populateView(storeId: String?, storeCity: String?) {
        binding.store.tvStoreName.text = storeId.toString()
        binding.store.tvStoreCity.text = "Kota $storeCity"
    }

    private fun setButtonClick() {
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
        const val EXTRA_STORE_ID = "EXTRA_STORE_ID"
        const val EXTRA_STORE_CITY = "EXTRA_STORE_CITY"
    }
}