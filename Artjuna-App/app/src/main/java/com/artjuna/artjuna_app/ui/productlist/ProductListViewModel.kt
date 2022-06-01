package com.artjuna.artjuna_app.ui.productlist

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class ProductListViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getRecommended() = mainRepository.getProduct()

}