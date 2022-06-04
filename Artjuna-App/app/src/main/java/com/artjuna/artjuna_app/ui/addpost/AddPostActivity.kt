package com.artjuna.artjuna_app.ui.addpost

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityAddPostBinding
import com.artjuna.artjuna_app.ui.loading.LoadingDialog
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding
    private val viewModel: AddPostViewModel by viewModel()
    private var photoFile: File? = null
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLoading()
        setButtonClick()
    }

    private fun setButtonClick() {
        with(binding) {
            btnAddPhoto.setOnClickListener { openGallery() }
            bottombar.btnUpload.setOnClickListener {
                if (formNotEmpty()){
                    uploadPost()
                }
            }
        }
    }

    private fun formNotEmpty(): Boolean {
        if(photoFile == null){
            AppUtils.showToast(this, "You haven't choose a photo")
            return false
        }
        if(binding.etCaption.text.isNullOrEmpty()){
            binding.etCaption.error = "Isi Dulu Bang"
            return false
        }
        if(binding.etProductName.text.isNullOrEmpty()){
            binding.etProductName.error = "Isi Dulu Bang"
            return false
        }
        return true
    }

    private fun uploadPost() {
        val post = Post()
        post.caption = binding.etCaption.text.toString()
        post.productName = binding.etProductName.text.toString()


        viewModel.uploadPost(post,photoFile!!).observe(this){
            when(it){
                is Result.Loading -> loadingDialog.show()
                is Result.Success -> {
                    loadingDialog.dismiss()
                    AppUtils.showToast(this, "Post Uploaded")
                    finish()
                    onBackPressed()
                }
                is Result.Error ->{
                    loadingDialog.dismiss()
                    AppUtils.showToast(this, it.error)
                }
            }
        }
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
            val myFile = AppUtils.uriToFile(selectedImg, this)
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

    private fun setupLoading() {
        loadingDialog = LoadingDialog(this,false)
    }

}