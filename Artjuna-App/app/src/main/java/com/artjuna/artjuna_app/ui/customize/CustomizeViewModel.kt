package com.artjuna.artjuna_app.ui.customize

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Product
import okhttp3.MultipartBody
import java.io.File

class CustomizeViewModel(private val mainRepository: MainRepository): ViewModel() {

    fun uploadImageForStyleTransfer(productId: String,image: File) = mainRepository.uploadImageForStyleTransfer(productId,image)
}