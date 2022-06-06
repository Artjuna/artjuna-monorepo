package com.artjuna.artjuna_app.ui.mystore.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ItemProductMystoreBinding
import com.artjuna.artjuna_app.ui.mystore.activity.addproduct.AddProductActivity
import com.artjuna.artjuna_app.utils.AppUtils.loadImage

class MyProductAdapter:ListAdapter<Product, MyProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    inner class ProductViewHolder(private val binding: ItemProductMystoreBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            with(binding){
                ivImage.loadImage(product.image)
                tvName.text = product.name
                tvPrice.text = "Rp ${product.price}"
                tvProductId.text = product.id

                btnEdit.setOnClickListener {
                    val intent = Intent(itemView.context, AddProductActivity::class.java)
                    intent.putExtra(AddProductActivity.TYPE, AddProductActivity.UPDATE)
                    intent.putExtra(AddProductActivity.PRODUCT, product)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductMystoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
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