package com.artjuna.artjuna_app.ui.checkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ActivityCheckoutBinding
import com.artjuna.artjuna_app.ui.address.AddressActivity
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCheckoutBinding
    private val checkoutViewModel:CheckoutViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonClick()
        getProduct()
        getAddress()
    }

    private fun setButtonClick() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
            address.btnChangeAddress.setOnClickListener {
                startActivity(Intent(this@CheckoutActivity,AddressActivity::class.java))
            }
        }
    }

    private fun getAddress(){
        populateViewAddress(checkoutViewModel.getAddress())
    }

    private fun populateViewAddress(address: Address) {
        with(binding.address){
            if(address.name == ""){
                tvNameNumber.text = "You haven't add your address"
                tvAddress.text = ""
            }else{
                tvNameNumber.text = "${address.name} (${address.number})"
                tvAddress.text = "${address.address} ID ${address.postalCode}"
            }
        }
    }

    private fun getProduct() {
        val extras = intent.extras
        if(extras!=null){
            val product = extras.getParcelable<Product>(EXTRA_PRODUCT)
            populateViewProduct(product!!)
        }
    }

    private fun populateViewProduct(product: Product) {
        with(binding.product){
            tvStoreName.text = product.storeName
            tvStoreCity.text = product.storeCity
            ivImage.loadImage(product.storeImage)
            tvName.text = product.name
            tvPrice.text = "Rp ${product.price}"
        }
    }

    override fun onResume() {
        super.onResume()
        getAddress()
    }
    companion object{
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }
}