package com.artjuna.artjuna_app.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ItemProductSmallBinding
import com.artjuna.artjuna_app.ui.detailproduct.DetailProductActivity
import com.artjuna.artjuna_app.utils.AppUtils.loadImage

class RecomAdapter:ListAdapter<Product, RecomAdapter.RecomViewHolder>(DIFF_CALLBACK) {

    class RecomViewHolder(private val binding: ItemProductSmallBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            with(binding){
                ivImage.loadImage(product.image)
                tvName.text = product.name
                tvPrice.text = "Rp ${product.price}"
                tvCity.text = product.city
                tvRatingSold.text = "${product.rating} | ${product.sold} sold"

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailProductActivity::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomViewHolder {
        val binding =
            ItemProductSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecomViewHolder, position: Int) {
        val product = getItem(position)
        if(product!=null){
            holder.bind(product)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}