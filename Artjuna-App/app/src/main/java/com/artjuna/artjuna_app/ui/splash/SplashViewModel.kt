package com.artjuna.artjuna_app.ui.splash

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.AuthRepository

class SplashViewModel(private val authRepository: AuthRepository): ViewModel() {

    fun getLoginStatus() = authRepository.getLoginStatus()
}