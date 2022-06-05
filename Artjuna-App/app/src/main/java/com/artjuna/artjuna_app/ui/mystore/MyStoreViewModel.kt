package com.artjuna.artjuna_app.ui.mystore

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class MyStoreViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getPost() = mainRepository.getPost()
    fun getProductByUserId(id:String) = mainRepository.getProductByUserId(id)


}