package com.artjuna.artjuna_app.ui.mystore.activity.orderhistory

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class OrderHistoryViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun getOrderHistory() = mainRepository.getOrderBySellerId()
}