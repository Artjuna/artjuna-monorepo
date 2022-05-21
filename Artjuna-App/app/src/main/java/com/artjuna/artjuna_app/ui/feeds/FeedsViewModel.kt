package com.artjuna.artjuna_app.ui.feeds

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository

class FeedsViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getPost() = mainRepository.getPost()
}