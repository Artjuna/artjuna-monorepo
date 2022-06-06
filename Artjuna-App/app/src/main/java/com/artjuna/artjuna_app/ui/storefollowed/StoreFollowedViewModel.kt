package com.artjuna.artjuna_app.ui.storefollowed

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.AuthRepository
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class StoreFollowedViewModel(private val mainRepository: MainRepository): ViewModel() {

    fun getAllStoreFollowed() = mainRepository.getAllStoreFollowed()
}