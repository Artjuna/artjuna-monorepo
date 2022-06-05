package com.artjuna.artjuna_app.ui.profilesettings

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.AuthRepository
import com.artjuna.artjuna_app.core.data.source.model.User

class ProfileSettingsViewModel(
    private val authRepository: AuthRepository) : ViewModel() {

    fun signOut() = authRepository.signOut()

    fun updateAccount(user:User) = authRepository.updateAccount(user)
    fun upgradeAccount(user:User) = authRepository.upgradeAccount(user)

}