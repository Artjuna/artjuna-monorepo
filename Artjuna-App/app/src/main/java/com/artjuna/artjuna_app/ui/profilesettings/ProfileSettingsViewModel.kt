package com.artjuna.artjuna_app.ui.profilesettings

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.AuthRepository

class ProfileSettingsViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun signOut() = authRepository.signOut()

}