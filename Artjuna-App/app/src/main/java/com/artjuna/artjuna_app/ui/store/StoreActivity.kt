package com.artjuna.artjuna_app.ui.store

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityStoreBinding
import com.artjuna.artjuna_app.ui.store.adapter.StorePagerAdapter
import com.artjuna.artjuna_app.utils.AppUtils
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
        getStoreId()
    }

    private fun getStoreId() {
        if(intent.extras != null){
            val storeId = intent.extras!!.getString(EXTRA_STORE_ID,"")
            getStoreData(storeId)
            showTabLayout(storeId)
        }
    }

    private fun getStoreData(storeId: String) {
        storeViewModel.getStoreDataById(storeId).observe(this){
            when(it){
                is Result.Success -> {
                    populateView(it.data)
                }
                is Result.Error -> {
                    AppUtils.showToast(this, it.error)
                }
            }
        }
    }

    private fun populateView(store:User) {
        binding.store.tvStoreName.text = store.fullName
        binding.store.tvStoreCity.text = "${store.city} ${store.province} | ${store.followers} Followers"
    }

    private fun setButtonClick() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun showTabLayout(storeId: String) {
        val storePagerAdapter = StorePagerAdapter(this)
        storePagerAdapter.storeId = storeId
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
    }
}