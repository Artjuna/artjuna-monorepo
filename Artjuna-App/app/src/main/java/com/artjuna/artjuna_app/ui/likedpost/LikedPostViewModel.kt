package com.artjuna.artjuna_app.ui.likedpost

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Post

class LikedPostViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getAllPostLiked() = mainRepository.getAllPostLiked()

    fun insertPostLiked(post: Post) = mainRepository.insertPostLiked(post)

    fun deletePostLikedById(id:String) = mainRepository.deletePostFromLiked(id)

}