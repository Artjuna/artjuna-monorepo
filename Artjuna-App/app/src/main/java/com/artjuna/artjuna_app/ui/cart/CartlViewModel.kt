package com.artjuna.artjuna_app.ui.cart

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Product

class CartlViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getAllProductInCart() = mainRepository.getAllProductInCart()


}