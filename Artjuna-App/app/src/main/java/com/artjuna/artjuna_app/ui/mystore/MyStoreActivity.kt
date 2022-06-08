package com.artjuna.artjuna_app.ui.mystore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.databinding.ActivityMyStoreBinding
import com.artjuna.artjuna_app.ui.mystore.activity.orderhistory.OrderHistoryActivity
import com.artjuna.artjuna_app.ui.mystore.adapter.MyStorePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MyStoreActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMyStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserData()
        setButtonClick()
    }

    private fun setButtonClick() {
        binding.header.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnOrderHistory.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }
    }

    private fun getUserData() {
        val user = intent.extras?.getParcelable<User>(EXTRA_USER)
        populateView(user!!)
        showTabLayout(user.id)
    }

    private fun populateView(user: User) {
        with(binding){
            header.tvName.text = user.fullName
            header.tvCityFollowers.text = "${user.city}, ${user.province}"
        }
    }

    private fun showTabLayout(userId:String) {
        val myStorePagerAdapter = MyStorePagerAdapter(this)
        myStorePagerAdapter.userId = userId
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