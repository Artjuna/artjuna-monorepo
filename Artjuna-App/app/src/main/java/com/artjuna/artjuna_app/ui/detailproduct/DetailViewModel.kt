package com.artjuna.artjuna_app.ui.detailproduct

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Product

class DetailViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun addHasSeen(productId:String) = mainRepository.addHasSeen(productId)

    fun checkIfProductInCart(id:String) = mainRepository.checkIfProductInCart(id)

    fun insertProductToCart(product: Product) = mainRepository.insertProductToCart(product)

    fun deleteProductFromCartById(id: String) = mainRepository.deleteProductFromCartById(id)

}