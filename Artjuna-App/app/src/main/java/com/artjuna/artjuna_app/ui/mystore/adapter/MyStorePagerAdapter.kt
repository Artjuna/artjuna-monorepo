package com.artjuna.artjuna_app.ui.mystore.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.artjuna.artjuna_app.ui.mystore.fragment.MyPostFragment
import com.artjuna.artjuna_app.ui.mystore.fragment.MyProductFragment
import com.artjuna.artjuna_app.ui.store.fragment.ProductFragment

class MyStorePagerAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {

    var userId:String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0 -> {
                fragment = MyProductFragment()
                fragment.arguments = Bundle().apply {
                    putString(MyProductFragment.USER_ID, userId)
                }
            }
            1 -> {
                fragment = MyPostFragment()
                fragment.arguments = Bundle().apply {
                    putString(MyPostFragment.USER_ID, userId)
                }
            }
        }
        return fragment as Fragment
    }

}