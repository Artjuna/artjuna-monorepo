package com.artjuna.artjuna_app.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ItemCategoryBinding
import com.artjuna.artjuna_app.databinding.ItemProductBinding
import com.artjuna.artjuna_app.databinding.ItemProductSmallBinding
import com.artjuna.artjuna_app.ui.detailproduct.DetailProductActivity
import com.artjuna.artjuna_app.utils.AppUtils.loadImage

class CatAdapter:ListAdapter<String, CatAdapter.RecomViewHolder>(DIFF_CALLBACK) {

    class RecomViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category:String){
            with(binding){
                tvCategoryName.text = category
                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, DetailProductActivity::class.java)
//                    intent.putExtra(DetailProductActivity.EXTRA_PRODUCT, product)
//                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecomViewHolder, position: Int) {
        val product = getItem(position)
        if(product!=null){
            holder.bind(product)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }


}