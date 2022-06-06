package com.artjuna.artjuna_app.ui.store.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.artjuna.artjuna_app.ui.store.fragment.PostFragment
import com.artjuna.artjuna_app.ui.store.fragment.ProductFragment

class StorePagerAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {

    var storeId:String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0 -> {
                fragment = ProductFragment()
                fragment.arguments = Bundle().apply {
                    putString(ProductFragment.STORE_ID, storeId)
                }
            }
            1 -> {
                fragment = PostFragment()
                fragment.arguments = Bundle().apply {
                    putString(PostFragment.STORE_ID, storeId)
                }
            }
        }
        return fragment as Fragment
    }

}