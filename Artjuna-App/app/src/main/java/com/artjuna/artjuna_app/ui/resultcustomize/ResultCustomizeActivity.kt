package com.artjuna.artjuna_app.ui.resultcustomize

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.remote.response.StyleTransferResponse
import com.artjuna.artjuna_app.databinding.ActivityResultCustomizeBinding
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage

class ResultCustomizeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResultCustomizeBinding

    companion object{
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        const val EXTRA_IMG = "EXTRA_IMG"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataProduct = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)
        nameAndPrice(dataProduct)

        image()

        binding.btnBack.setOnClickListener { onBackPressed() }


    }



    private fun nameAndPrice(product: Product?){
        if (product != null){
            with(binding){
                tvProductName.text = product.name
                tvProductPrice.text = "Rp ${product.price}"
            }
        }
    }

    private fun image(){
        val extras = intent.extras
        if (extras != null){
            val img = extras.getString(EXTRA_IMG)
            binding.ivResultCustom.loadImage(img)


        }
    }

}