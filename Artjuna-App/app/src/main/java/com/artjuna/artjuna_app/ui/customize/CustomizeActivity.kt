package com.artjuna.artjuna_app.ui.customize

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ActivityCustomizeBinding
import com.artjuna.artjuna_app.ui.resultcustomize.ResultCustomizeActivity
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import com.artjuna.artjuna_app.utils.AppUtils.uriToFile
import java.io.File

class CustomizeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCustomizeBinding
    private var photoFile: File? = null

    companion object{
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataProduct = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)
        populateViewProduct(dataProduct)

        setButton()

    }

    private fun populateViewProduct(product: Product?) {
        if (product != null){
            with(binding){
                ivProduct.loadImage(product.image)
                tvProductName.text = product.name
                tvProductPrice.text = "Rp ${product.price}"
            }
        }
    }

    private fun setButton(){
        with(binding){
            btnAddPhoto.setOnClickListener { openGallery() }
            btnBack.setOnClickListener { onBackPressed() }
            bottomBar.btnCustomWithAI.setOnClickListener {
                startActivity(Intent(this@CustomizeActivity, ResultCustomizeActivity::class.java))
            }
            bottomBar.btnCustomWithYourDesign.setOnClickListener {
                startActivity(Intent(this@CustomizeActivity, CustomizeWithYourDesignActivity::class.java))
            }

        }
    }

    private fun customize(){

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
            val myFile = uriToFile(selectedImg, this)

            photoFile = myFile

            binding.ivAddPhoto.setImageURI(selectedImg)
        }
    }

}