package com.artjuna.artjuna_app.ui.mystore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.databinding.ActivityMyStoreBinding
import com.artjuna.artjuna_app.ui.mystore.adapter.MyStorePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MyStoreActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMyStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserData()
        showTabLayout()
        setButtonClick()
    }

    private fun setButtonClick() {
        binding.header.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getUserData() {
        val user = intent.extras?.getParcelable<User>(EXTRA_USER)
        populateView(user!!)
    }

    private fun populateView(user: User) {
        with(binding){
            header.tvName.text = user.fullName
            header.tvCityFollowers.text = "Kota ${user.city} | ${user.followers} Followers"
        }
    }

    private fun showTabLayout() {
        val myStorePagerAdapter = MyStorePagerAdapter(this)
        with(binding){
            viewPager.adapter = myStorePagerAdapter
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
        const val EXTRA_USER = "EXTRA_USER"
    }
}