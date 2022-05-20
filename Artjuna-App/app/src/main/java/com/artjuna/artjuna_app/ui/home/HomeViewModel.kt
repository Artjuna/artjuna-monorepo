package com.artjuna.artjuna_app.ui.home

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class HomeViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getRecommended() = mainRepository.getProduct()

}