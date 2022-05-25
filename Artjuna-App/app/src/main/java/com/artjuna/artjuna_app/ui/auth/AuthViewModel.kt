package com.artjuna.artjuna_app.ui.auth

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.AuthRepository
import com.artjuna.artjuna_app.core.data.source.model.User

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val isLogged = authRepository.isLogged
    val isLoading = authRepository.isLoading
    val message = authRepository.message

    fun signUp(user: User) = authRepository.checkUsernameAvail(user)
    fun signIn(email:String, password:String) = authRepository.signIn(email, password)

}