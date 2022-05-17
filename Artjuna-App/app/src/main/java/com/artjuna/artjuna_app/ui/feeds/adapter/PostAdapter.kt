package com.artjuna.artjuna_app.ui.feeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.databinding.ItemPostBinding
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage

class PostAdapter:ListAdapter<Post, PostAdapter.PostViewHolder>(DIFF_CALLBACK) {

    class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post){
            with(binding){
                ivUserImage.loadImage(post.userImage)
                tvUsername.text = post.userName
                ivPostImage.loadImage(post.image)
                tvProductName.text = post.productName
                tvLiked.text = AppUtils.getLikedBy(post.like)
                tvNameCaption.text = AppUtils.getUserandCaption(post.userName, post.caption)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        if(post!=null){
            holder.bind(post)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}