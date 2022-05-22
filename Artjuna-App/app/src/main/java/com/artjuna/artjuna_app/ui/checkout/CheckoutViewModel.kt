package com.artjuna.artjuna_app.ui.checkout

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class CheckoutViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getAddress() = mainRepository.getAddress()

}