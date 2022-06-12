package com.artjuna.artjuna_app.ui.checkout

import android.R
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ActivityCheckoutBinding
import com.artjuna.artjuna_app.ui.address.AddressActivity
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class CheckoutCustomActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private val checkoutViewModel:CheckoutViewModel by viewModel()
    private var product = Product()
    private var photoFile: File? = null



    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        const val EXTRA_IMG = "EXTRA_IMG"

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
                startActivity(Intent(this@CheckoutCustomActivity, AddressActivity::class.java))
            }
            bottomBar.btnOrder.setOnClickListener {
                AppUtils.sendOrderToWA(this@CheckoutCustomActivity, "6285210938775", this@CheckoutCustomActivity.product )
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

        if (intent.hasExtra(EXTRA_IMG)) {
            val bitM = BitmapFactory.decodeByteArray(
                intent.getByteArrayExtra(EXTRA_IMG),
                0,
                intent.getByteArrayExtra(EXTRA_IMG)!!.size
            )
            binding.product.ivImage.setImageBitmap(bitM)
        }

        val extras = intent.extras
        if (extras != null){
            val dataProduct = extras.getParcelable<Product>(EXTRA_PRODUCT)




            this.product = dataProduct!!
            populateViewProduct(dataProduct)
            populatePrice(dataProduct.price)

        } else {
            AppUtils.showToast(this, "ada null")
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