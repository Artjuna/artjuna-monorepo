package com.artjuna.artjuna_app.ui.addproduct

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Product

class AddProductViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun uploadProduct(product: Product) = mainRepository.uploadProduct(product)

}