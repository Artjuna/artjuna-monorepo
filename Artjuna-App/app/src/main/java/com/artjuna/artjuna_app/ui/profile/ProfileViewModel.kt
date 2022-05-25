package com.artjuna.artjuna_app.ui.profile

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class ProfileViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun getUser() = mainRepository.getUser()
}