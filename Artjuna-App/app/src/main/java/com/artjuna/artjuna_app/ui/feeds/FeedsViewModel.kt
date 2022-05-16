package com.artjuna.artjuna_app.ui.feeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Feeds Fragment"
    }
    val text: LiveData<String> = _text
}