package com.artjuna.artjuna_app.ui.mystore.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.artjuna.artjuna_app.ui.mystore.fragment.PostkFragment
import com.artjuna.artjuna_app.ui.mystore.fragment.ProductFragment

class SectionsPagerAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {

    var username:String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0 -> {
                fragment = ProductFragment()
//                fragment.arguments = Bundle().apply {
//                    putString(ProductFragment.USERNAME, username)
//                }
            }
            1 -> {
                fragment = PostkFragment()
//                fragment.arguments = Bundle().apply {
//                    putString(PostkFragment.USERNAME, username)
//                }
            }
        }
        return fragment as Fragment
    }

}