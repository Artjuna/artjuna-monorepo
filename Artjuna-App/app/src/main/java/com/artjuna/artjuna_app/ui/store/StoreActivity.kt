package com.artjuna.artjuna_app.ui.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityStoreBinding
import com.artjuna.artjuna_app.ui.store.adapter.StorePagerAdapter
import com.artjuna.artjuna_app.utils.AppUtils
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoreActivity : AppCompatActivity() {

    private val viewModel:StoreViewModel by viewModel()
    private lateinit var binding:ActivityStoreBinding

    private var storeId = ""
    private var store = User()
    private var isFollowed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonClick()
        getStoreId()
    }

    private fun getStoreId() {
        if(intent.extras != null){
            storeId = intent.extras!!.getString(EXTRA_STORE_ID,"")
            getStoreData()
            checkIfStoreFollowed()
            showTabLayout()
        }
    }

    private fun checkIfStoreFollowed() {
        viewModel.checkIfStoreFollowedById(storeId).observe(this){
            isFollowed = it
            setButtonFollowIcon()
        }
    }

    private fun setButtonFollowIcon() {
        binding.store.btnFollow.text = if(isFollowed) "Unfollow" else "Follow"
    }

    private fun getStoreData() {
        viewModel.getStoreDataById(storeId).observe(this){
            when(it){
                is Result.Success -> {
                    store = it.data
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
            store.btnFollow.setOnClickListener { onFollowClicked() }
        }
    }

    private fun onFollowClicked() {
        if (isFollowed){
            isFollowed = false
            unfollowStore()
        }else{
            isFollowed = true
            followStore()
        }
    }

    private fun followStore() {
        viewModel.insertStoreFollowed(store)
        viewModel.followStoreById(store.id)
        setButtonFollowIcon()
        AppUtils.showToast(this, "Store followed")
    }

    private fun unfollowStore() {
        viewModel.deleteStoreFollowedById(storeId)
        setButtonFollowIcon()
        AppUtils.showToast(this, "Store unfollowed")
    }



    private fun showTabLayout() {
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