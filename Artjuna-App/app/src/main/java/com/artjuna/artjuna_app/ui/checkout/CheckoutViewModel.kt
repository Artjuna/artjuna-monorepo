package com.artjuna.artjuna_app.ui.checkout

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Order

class CheckoutViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getAddress() = mainRepository.getAddress()

    fun getStoreDataById(id:String) = mainRepository.getStoreById(id)

    fun addOrder(order: Order) = mainRepository.addOrder(order)

}