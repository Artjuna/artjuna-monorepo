package com.artjuna.artjuna_app.ui.feeds

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Post

class FeedsViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getPost() = mainRepository.getPost()

    fun getAllPostLikedId() = mainRepository.getAllPostLikedId()

    fun insertPostLiked(post:Post) = mainRepository.insertPostLiked(post)

    fun deletePostLikedById(id:String) = mainRepository.deletePostFromLiked(id)

    fun likePostById(id:String) = mainRepository.likePost(id)
}