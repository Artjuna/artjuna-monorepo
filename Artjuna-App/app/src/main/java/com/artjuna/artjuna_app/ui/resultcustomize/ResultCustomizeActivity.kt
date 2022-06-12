package com.artjuna.artjuna_app.ui.resultcustomize

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ActivityResultCustomizeBinding
import com.artjuna.artjuna_app.ui.checkout.CheckoutCustomActivity
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage

class ResultCustomizeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResultCustomizeBinding

    companion object{
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        const val EXTRA_IMG = "EXTRA_IMG"
    }

    private var product = Product()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataProduct()

        getCustomizedImage()

        setButton()



    }

    private fun getDataProduct() {
        product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)!!
        populateProduct()
    }


    private fun populateProduct(){
        with(binding){
            tvProductName.text = product.name
            tvProductPrice.text = "Rp ${product.price}"
        }
    }

    private fun getCustomizedImage(){
        val imgB64 = intent.extras?.getString(EXTRA_IMG)
        val imgBitmap = AppUtils.convertBase64toBitmap(imgB64!!)
        binding.ivResultCustom.loadImage(imgBitmap)
    }

    private fun setButton(){
        with(binding){
            product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)!!
            val imgB64 = intent.extras?.getString(EXTRA_IMG)
            bottomBar.btnCheckout.setOnClickListener {
                Intent(this@ResultCustomizeActivity, CheckoutCustomActivity::class.java).also { intent ->
                    intent.putExtra(CheckoutCustomActivity.EXTRA_IMG, imgB64)
                    intent.putExtra(CheckoutCustomActivity.EXTRA_PRODUCT, product)
                    startActivity(intent)
                    finish()
                }
            }



        }
    }

}