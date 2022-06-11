package com.artjuna.artjuna_app.ui.store

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.User

class StoreViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getPost() = mainRepository.getPost()
    fun getProductByUserId(id:String) = mainRepository.getProductByUserId(id)

    fun getStoreDataById(id:String) = mainRepository.getStoreById(id)

    fun checkIfStoreFollowedById(id:String) = mainRepository.checkIfStoreFollowedById(id)

    fun deleteStoreFollowedById(id:String) = mainRepository.deleteStoreFollowedById(id)

    fun insertStoreFollowed(store: User) = mainRepository.insertStoreFollowed(store)

    fun getPostByUserId(id: String) = mainRepository.getPostByUserId(id)

    fun followStoreById(id:String) = mainRepository.followStore(id)

}