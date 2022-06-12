package com.artjuna.artjuna_app.ui.customize

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityCustomizeBinding
import com.artjuna.artjuna_app.ui.loading.LoadingDialog
import com.artjuna.artjuna_app.ui.resultcustomize.ResultCustomizeActivity
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import com.artjuna.artjuna_app.utils.AppUtils.uriToFile
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class CustomizeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCustomizeBinding
    private val viewModel: CustomizeViewModel by viewModel()
    private var photoFile: File? = null
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

        val dataProduct = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)
        populateViewProduct(dataProduct)

        setButton()

    }

    private fun populateViewProduct(product: Product?) {
        if (product != null){
            with(binding){
                ivProduct.loadImage(AppUtils.getProductImageURL(product.image))
                tvProductName.text = product.name
                tvProductPrice.text = "Rp ${product.price}"
            }
        }
    }



    private fun setButton(){

        val dataProduct = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)

        with(binding){
            btnAddPhoto.setOnClickListener { openGallery() }
            btnBack.setOnClickListener { onBackPressed() }
            bottomBar.btnCustomWithAI.setOnClickListener {customImageStyleTransfer()}
            bottomBar.btnCustomWithYourDesign.setOnClickListener {
                Intent(this@CustomizeActivity, CustomizeWithYourDesignActivity::class.java).also { intent ->
                    intent.putExtra(CustomizeWithYourDesignActivity.EXTRA_PRODUCT, dataProduct)
                    startActivity(intent)
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
        val dataProduct = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)
        val id = dataProduct?.id
        var isValid = true

        if (photoFile == null){
            Snackbar.make(binding.root, "Please select an image", Snackbar.LENGTH_SHORT).show()
            isValid = false
        }

        if (isValid) {

            viewModel.uploadStyleTransfer(id!!, photoFile!!).observe(this) {
                when (it) {
                    is Result.Loading -> loadingDialog.show()
                    is Result.Success -> {
                        loadingDialog.dismiss()

                        val body = it.data.stylizedImage
                        val iBitmap = AppUtils.convertBase64toBitmap(body!!)

                        Intent(
                            this@CustomizeActivity,
                            ResultCustomizeActivity::class.java
                        ).also { intent ->
                            intent.putExtra(ResultCustomizeActivity.EXTRA_IMG, iBitmap)
                            startActivity(intent)
                        }

                        Intent(
                            this@CustomizeActivity,
                            ResultCustomizeActivity::class.java
                        ).also { intent ->
                            intent.putExtra(ResultCustomizeActivity.EXTRA_PRODUCT, dataProduct)
                            startActivity(intent)
                        }


                    }
                    is Result.Error -> {
                        loadingDialog.dismiss()
                        AppUtils.showToast(this, it.error)
                    }
                }
            }
        }
    }

}