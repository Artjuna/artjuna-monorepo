package com.artjuna.artjuna_app.ui.mystore

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Product

class MyStoreViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getPost() = mainRepository.getPost()
    fun getProductByUserId(id:String) = mainRepository.getProductByUserId(id)

    fun getPostByUserId(id: String) = mainRepository.getPostByUserId(id)




}