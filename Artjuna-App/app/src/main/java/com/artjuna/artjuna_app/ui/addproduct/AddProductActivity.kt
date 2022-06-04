package com.artjuna.artjuna_app.ui.addproduct

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityAddProductBinding
import com.artjuna.artjuna_app.ui.loading.LoadingDialog
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import com.artjuna.artjuna_app.utils.AppUtils.uriToFile
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.ArrayList

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddProductBinding
    private val viewModel:AddProductViewModel by viewModel()
    private lateinit var loadingDialog: LoadingDialog
    private var photoFile: File? = null
    private var category:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLoading()
        setButtonClick()
        getCategoryList()
        setCategoryListener()
    }

    private fun getCategoryList() {
        viewModel.getCategories().observe(this){
            when(it){
                is Result.Success -> addCategoryFromList(it.data)
            }
        }

    }

    private fun addCategoryFromList(list: List<String>) {
        list.map {
            val chipCat = Chip(this)
            chipCat.text = it
            chipCat.isClickable = true
            chipCat.isCheckable = true
            binding.categories.catGroup.addView(chipCat)
        }
        setCategoryListener()
    }

    private fun setCategoryListener() {
        binding.categories.catGroup.setOnCheckedChangeListener {chipGroup,i ->
            val checkedChip = findViewById<Chip>(i)
            category = checkedChip.text.toString()
        }
    }

    private fun setButtonClick() {
        with(binding){
            btnAddPhoto.setOnClickListener { openGallery() }
            bottombar.btnUpload.setOnClickListener {
                if(formNotEmpty()){
                    uploadProduct()
                }
            }
        }
    }

    private fun uploadProduct() {
        val product = collectProductFromForm()
        viewModel.uploadProduct(product, photoFile!!).observe(this){
            when(it){
                is Result.Loading -> loadingDialog.show()
                is Result.Error -> {
                    loadingDialog.dismiss()
                    AppUtils.showToast(this, it.error)
                }
                is Result.Success -> {
                    loadingDialog.dismiss()
                    AppUtils.showToast(this, "Product Uploaded")
                    finish()
                }
            }
        }
    }

    private fun collectProductFromForm(): Product {
        val product = Product()
        product.name = binding.etProductName.text.toString()
        product.price = binding.etProductPrice.text.toString().toInt()
        product.detail = binding.etDetail.text.toString()
        product.category = category!!
        product.isCustomizable = binding.custom.isActivated
        return product
    }

    private fun formNotEmpty(): Boolean {
        if(photoFile == null){
            AppUtils.showToast(this, "You haven't choose a photo")
            return false
        }
        if(binding.etProductName.text.isNullOrEmpty()){
            binding.etProductName.error = "Must be filled"
            return false
        }
        if(binding.etProductPrice.text.isNullOrEmpty()){
            binding.etProductPrice.error = "Must be filled"
            return false
        }
        if(binding.etDetail.text.isNullOrEmpty()){
            binding.etDetail.error = "Must be filled"
            return false
        }
        if(category.isNullOrEmpty()){
            AppUtils.showToast(this, "You haven't choose a category")
            return false
        }

        return true
    }

    private fun setupLoading() {
        loadingDialog = LoadingDialog(this,false)
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)

            photoFile = myFile

            val size = AppUtils.getImageSizeInKB(myFile)
            if(size > 1024){
                AppUtils.showToast(this, "Image size too large")
                photoFile = null
                binding.ivImage.loadImage("")
            }else{
                binding.ivImage.loadImage(selectedImg)
            }
        }
    }

}