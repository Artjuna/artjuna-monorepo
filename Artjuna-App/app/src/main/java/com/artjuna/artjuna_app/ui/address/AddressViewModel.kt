package com.artjuna.artjuna_app.ui.address

import androidx.lifecycle.ViewModel
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.model.Address

class AddressViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getAddress() = mainRepository.getAddress()
    fun setAddress(address: Address) = mainRepository.setAddress(address)

}