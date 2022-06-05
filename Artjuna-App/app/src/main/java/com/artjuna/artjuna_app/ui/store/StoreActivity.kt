package com.artjuna.artjuna_app.ui.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.databinding.ActivityStoreBinding
import com.artjuna.artjuna_app.ui.store.adapter.StorePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoreActivity : AppCompatActivity() {

    private val storeViewModel:StoreViewModel by viewModel()
    private lateinit var binding:ActivityStoreBinding
    private var store = User()
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
            store = intent.extras!!.getParcelable<User>(EXTRA_STORE)!!
            populateView()
        }
    }

    private fun populateView() {
        binding.store.tvStoreName.text = store.fullName
        binding.store.tvStoreCity.text = "${store.city} ${store.province}"
    }

    private fun setButtonClick() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun showTabLayout() {
        val storePagerAdapter = StorePagerAdapter(this)
        storePagerAdapter.storeId = store.id
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
        const val EXTRA_STORE = "EXTRA_STORE"
    }
}