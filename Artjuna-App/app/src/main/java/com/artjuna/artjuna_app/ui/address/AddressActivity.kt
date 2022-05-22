package com.artjuna.artjuna_app.ui.address

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.databinding.ActivityAddressBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressActivity : AppCompatActivity() {
    private val viewModel:AddressViewModel by viewModel()
    private lateinit var binding:ActivityAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAddress()
        setButtonClick()
    }

    private fun setButtonClick() {
        binding.btnSave.setOnClickListener {
            if(formNotEmpty()){
                saveAddress()
                onBackPressed()
            }
        }
    }

    private fun saveAddress() {
        val address = Address(
            name = binding.etFullName.text.toString(),
            number = binding.etNumber.text.toString(),
            address = binding.etAddress.text.toString(),
            postalCode = binding.etPostalCode.text.toString(),
        )
        viewModel.setAddress(address)
    }

    private fun formNotEmpty(): Boolean {
        with(binding){
            if(etFullName.text.isNullOrEmpty()){
                return false
            }
            if(etNumber.text.isNullOrEmpty()){
                return false
            }
            if(etAddress.text.isNullOrEmpty()){
                return false
            }
            if(etPostalCode.text.isNullOrEmpty()){
                return false
            }
            return true
        }
    }

    private fun getAddress() {
        populateViewAddress(viewModel.getAddress())
    }

    private fun populateViewAddress(address: Address) {
        with(binding){
            etFullName.setText(address.name)
            etNumber.setText(address.number)
            etAddress.setText(address.address)
            etPostalCode.setText(address.postalCode)
        }
    }
}