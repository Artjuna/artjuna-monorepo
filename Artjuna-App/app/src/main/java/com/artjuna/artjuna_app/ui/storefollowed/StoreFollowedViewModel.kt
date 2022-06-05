package com.artjuna.artjuna_app.ui.storefollowed

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.AuthRepository

class StoreFollowedViewModel(private val authRepository: AuthRepository): ViewModel() {

    fun getLoginStatus() = authRepository.getLoginStatus()
}