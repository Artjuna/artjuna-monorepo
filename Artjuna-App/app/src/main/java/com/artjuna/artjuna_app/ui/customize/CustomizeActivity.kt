package com.artjuna.artjuna_app.ui.customize

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.R
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityCustomizeBinding
import com.artjuna.artjuna_app.ui.loading.LoadingDialog
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import com.artjuna.artjuna_app.utils.AppUtils.uriToFile
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class CustomizeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCustomizeBinding
    private val viewModel: CustomizeViewModel by viewModel()
    private var photoFile: File? = null
    private var product = Product()
    private lateinit var loadingDialog: LoadingDialog

    companion object{
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        const val EXTRA_IMG = "EXTRA_IMG"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLoading()
        getProductData()
        setButton()

    }

    private fun getProductData() {
        product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)!!
        populateViewProduct()
    }

    private fun populateViewProduct() {
        with(binding){
            ivProduct.loadImage(AppUtils.getProductImageURL(product.image))
            tvProductName.text = product.name
            tvProductPrice.text = "Rp ${product.price}"
        }
    }



    private fun setButton(){
        with(binding){
            btnAddPhoto.setOnClickListener { openGallery() }
            btnBack.setOnClickListener { onBackPressed() }
            bottomBar.btnCustomWithAI.setOnClickListener { sendCustomImage() }
            bottomBar.btnCustomWithYourDesign.setOnClickListener {
                Intent(this@CustomizeActivity, CustomizeWithYourDesignActivity::class.java).also { intent ->
                    intent.putExtra(CustomizeWithYourDesignActivity.EXTRA_PRODUCT, product)
                    startActivity(intent)
                }
            }

        }
    }

    private fun sendCustomImage() {
        if(photoFile!=null){
            viewModel.uploadImageForStyleTransfer(product.id, photoFile!!).observe(this){
                when(it){
                    is Result.Loading -> loadingDialog.show()
                    is Result.Error -> {
                        loadingDialog.dismiss()
                        AppUtils.showToast(this, it.error)
                    }
                    is Result.Success -> {
                        loadingDialog.dismiss()
                        Log.d("okhttp", it.data)
                        val img = AppUtils.convertBase64toByteArray(it.data)
                        binding.ivStyleTransfer.loadImage(img)
                    }
                }
            }
        }
    }

    private fun setupLoading() {
        loadingDialog = LoadingDialog(this,false)
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

    private fun customImageStyleTransfer(){
        if (photoFile!=null){
            viewModel.uploadStyleTransfer(product.id, photoFile!!).observe(this){
                when(it){
                    is Result.Success -> {
                        val img = it.data

                    }
                }
            }
        }else{
            Snackbar.make(binding.root, "Please select an image", Snackbar.LENGTH_SHORT).show()
        }
    }

}