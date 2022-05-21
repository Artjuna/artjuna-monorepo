package com.artjuna.artjuna_app.ui.mystore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivityMyStoreBinding
import com.artjuna.artjuna_app.ui.mystore.adapter.MyStorePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MyStoreActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMyStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showTabLayout()
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
    }
}