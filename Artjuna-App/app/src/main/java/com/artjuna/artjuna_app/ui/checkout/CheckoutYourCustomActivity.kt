package com.artjuna.artjuna_app.ui.checkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ActivityCheckoutBinding
import com.artjuna.artjuna_app.ui.address.AddressActivity
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class CheckoutYourCustomActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private val checkoutViewModel:CheckoutViewModel by viewModel()
    private var product = Product()

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        const val EXTRA_IMAGE = "EXTRA_IMAGE"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonCLick()
        getProduct()
        getAddress()



    }

    private fun setButtonCLick(){
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
            address.btnChangeAddress.setOnClickListener {
                startActivity(Intent(this@CheckoutYourCustomActivity, AddressActivity::class.java))
            }
            bottomBar.btnOrder.setOnClickListener {
                AppUtils.sendOrderToWA(this@CheckoutYourCustomActivity, "6285210938775", this@CheckoutYourCustomActivity.product )
            }
        }

    }

    private fun getAddress(){
        populateViewAddress(checkoutViewModel.getAddress())
    }

    private fun populateViewAddress(address: Address){
        with(binding.address){
            if (address.name == ""){
                tvNameNumber.text = "You haven't add your address"
                tvAddress.text = ""
            } else {
                tvNameNumber.text = "${address.name} (${address.number})"
                tvAddress.text = "${address.address} (${address.postalCode})"
            }
        }
    }



    private fun getProduct(){
        val extras = intent.extras
        if (extras != null){

            val img = extras.getString(EXTRA_IMAGE)
            val picUri = img?.toUri()
            binding.product.ivImage.setImageURI(picUri)

            val dataProduct = extras.getParcelable<Product>(EXTRA_PRODUCT)
            this.product = dataProduct!!
            populateViewProduct(dataProduct)
            populatePrice(dataProduct.price)

        }
    }

    private fun populatePrice(price: Int){
        val mPrice = "Rp $price"
        binding.price.tvPrice.text = mPrice
        binding.bottomBar.tvPrice.text = mPrice
    }

    private fun populateViewProduct(product: Product){
        with(binding.product){
            tvStoreName.text = product.storeName
            tvStoreCity.text = product.storeCity
            tvName.text = product.name
            tvPrice.text = "Rp ${product.price}"
        }
    }


    override fun onResume() {
        super.onResume()
        getAddress()
    }




}