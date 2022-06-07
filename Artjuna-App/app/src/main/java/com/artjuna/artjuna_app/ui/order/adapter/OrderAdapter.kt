package com.artjuna.artjuna_app.ui.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artjuna.artjuna_app.R
import com.artjuna.artjuna_app.core.data.source.model.Order
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.databinding.ItemPostBinding
import com.artjuna.artjuna_app.databinding.ItemProductOrderBinding
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage

class OrderAdapter:ListAdapter<Order, OrderAdapter.PostViewHolder>(DIFF_CALLBACK) {

    inner class PostViewHolder(private val binding: ItemProductOrderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(order: Order){
            with(binding){
                tvStoreName.text = order.storeName
                tvDate.text = order.date
                ivImage.loadImage(order.image)
                tvProductName.text = order.productName
                tvPrice.text = "Rp ${order.price}"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            ItemProductOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val order = getItem(position)
        if(order!=null){
            holder.bind(order)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}