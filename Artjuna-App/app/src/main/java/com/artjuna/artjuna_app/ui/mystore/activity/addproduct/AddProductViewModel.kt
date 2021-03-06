package com.artjuna.artjuna_app.ui.mystore.activity.addproduct

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Product
import java.io.File

class AddProductViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun uploadProduct(product: Product, image: File) = mainRepository.uploadProduct(product,image)
    fun getCategories() = mainRepository.getCategory()
    fun updateProduct(product: Product) = mainRepository.updateProduct(product)

}