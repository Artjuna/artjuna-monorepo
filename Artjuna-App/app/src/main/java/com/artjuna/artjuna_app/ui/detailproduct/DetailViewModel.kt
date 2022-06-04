package com.artjuna.artjuna_app.ui.detailproduct

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class DetailViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun addHasSeen(productId:String) = mainRepository.addHasSeen(productId)

}