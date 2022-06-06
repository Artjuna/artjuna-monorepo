package com.artjuna.artjuna_app.ui.storefollowed.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artjuna.artjuna_app.R
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.databinding.ItemPostBinding
import com.artjuna.artjuna_app.databinding.ItemStoreBinding
import com.artjuna.artjuna_app.ui.store.StoreActivity
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage

class StoreAdapter:ListAdapter<User, StoreAdapter.StoreViewHolder>(DIFF_CALLBACK) {

    inner class StoreViewHolder(private val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(store:User){
            with(binding){
                tvStoreName.text = store.fullName
                tvStoreCity.text = "${store.city} ${store.province}"

                btnVisitstore.setOnClickListener {
                    val intent = Intent(itemView.context, StoreActivity::class.java)
                    intent.putExtra(StoreActivity.EXTRA_STORE_ID, store.id)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding =
            ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = getItem(position)
        if(store!=null){
            holder.bind(store)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}