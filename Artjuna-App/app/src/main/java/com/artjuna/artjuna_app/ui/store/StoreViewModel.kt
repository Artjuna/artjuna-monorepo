package com.artjuna.artjuna_app.ui.store

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class StoreViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getPost() = mainRepository.getPost()
    fun getProductByUserId(id:String) = mainRepository.getProductByUserId(id)

}