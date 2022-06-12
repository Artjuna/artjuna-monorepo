package com.artjuna.artjuna_app.ui.customize

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ActivityCustomizeWithYouDesignBinding
import com.artjuna.artjuna_app.ui.checkout.CheckoutCustomActivity
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import com.google.android.material.snackbar.Snackbar
import java.io.File

class CustomizeWithYourDesignActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCustomizeWithYouDesignBinding
    private var photoFile: File? = null
    companion object{
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizeWithYouDesignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataProduct = intent.getParcelableExtra<Product>(CustomizeActivity.EXTRA_PRODUCT)
        populateViewProduct(dataProduct)


        setButton()
    }

    private fun openGallery(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == RESULT_OK){
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = AppUtils.uriToFile(selectedImg, this)

            photoFile = myFile

            binding.ivAddPhoto.setImageURI(selectedImg)
        }
    }

    private fun setButton(){
        with(binding){

            val dataProduct = intent.getParcelableExtra<Product>(CustomizeActivity.EXTRA_PRODUCT)


            btnAddPhoto.setOnClickListener { openGallery() }
            bottombar.btnCheckout.setOnClickListener {
                if (photoFile == null){
                    Snackbar.make(binding.root, "Please select an image", Snackbar.LENGTH_SHORT).show()
                } else{
                    val pByte = photoFile!!.readBytes()
                    Intent(this@CustomizeWithYourDesignActivity, CheckoutCustomActivity::class.java).also { intent ->
                        intent.putExtra( CheckoutCustomActivity.EXTRA_IMG ,pByte)
                        startActivity(intent)

                    }
                }


                Intent(this@CustomizeWithYourDesignActivity, CheckoutCustomActivity::class.java).also { intent ->
                    intent.putExtra(CheckoutCustomActivity.EXTRA_PRODUCT, dataProduct)
                    startActivity(intent)
                }

            }
        }
    }

    private fun populateViewProduct(product: Product?) {
        if (product != null){
            with(binding){
                tvProductName.text = product.name
                tvProductPrice.text = "Rp ${product.price}"
            }
        }
    }




}