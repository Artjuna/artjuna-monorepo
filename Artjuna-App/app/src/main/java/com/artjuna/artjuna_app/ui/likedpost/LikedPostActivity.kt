package com.artjuna.artjuna_app.ui.likedpost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.databinding.ActivityLikedPostBinding
import com.artjuna.artjuna_app.ui.adapter.PostAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class LikedPostActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLikedPostBinding
    private val viewModel:LikedPostViewModel by viewModel()
    private val postAdapter = PostAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        getListPost()
        setButtonLikeClicked()
        setButtonClicked()
    }

    private fun getListPost() {
        viewModel.getAllPostLiked().observe(this){
            postAdapter.submitList(it)
        }
    }

    private fun setButtonLikeClicked() {
        postAdapter.onLikeClick = {post ->
            if(post.isLiked){
                addPostLiked(post)
            }else{
                deletePostLiked(post.id)
            }
        }
    }

    private fun deletePostLiked(id: String) {
        viewModel.deletePostLikedById(id)
    }

    private fun addPostLiked(post: Post) {
        viewModel.insertPostLiked(post)
    }

    private fun setButtonClicked() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun setupAdapter() {
        binding.rvPost.adapter = postAdapter
    }
}