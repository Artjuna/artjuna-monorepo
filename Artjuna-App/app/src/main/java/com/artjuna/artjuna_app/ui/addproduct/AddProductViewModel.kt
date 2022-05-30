package com.artjuna.artjuna_app.ui.addproduct

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Post

class AddProductViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun uploadPost(post:Post) = mainRepository.uploadPost(post)

}