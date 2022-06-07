package com.artjuna.artjuna_app.ui.order

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class OrderViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun getOrderHistory() = mainRepository.getOrderByBuyerId()
}