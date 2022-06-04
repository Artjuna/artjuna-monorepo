package com.artjuna.artjuna_app.ui.addpost

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Post
import java.io.File

class AddPostViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun uploadPost(post:Post,image: File) = mainRepository.uploadPost(post,image)

}