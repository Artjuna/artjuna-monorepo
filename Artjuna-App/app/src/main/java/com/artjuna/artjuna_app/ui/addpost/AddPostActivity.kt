package com.artjuna.artjuna_app.ui.addpost

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.databinding.ActivityAddPostBinding
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import java.io.File


class AddPostActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddPostBinding
    private var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonClick()
    }

    private fun setButtonClick() {
        with(binding){
            btnAddPhoto.setOnClickListener { openGallery() }
            bottombar.btnUpload.setOnClickListener { uploadPost() }
        }
    }

    private fun uploadPost() {
        val post = Post()
        post.image = AppUtils.convertImageToBase64(photoFile!!)
        post.caption = binding.etCaption.text.toString()
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
//            val imageBase = AppUtils.convertImageToBase64(myFile)
//            val imageByte = AppUtils.convertBase64toByteArray(imageBase)
//            val imageBitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)

            binding.ivImage.loadImage(selectedImg)
        }
    }
}