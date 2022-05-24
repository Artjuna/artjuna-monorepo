package com.artjuna.artjuna_app.ui.addproduct

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivityAddProductBinding
import com.artjuna.artjuna_app.utils.AppUtils.uriToFile
import java.io.File

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddProductBinding

    private var photoFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonClick()
    }

    private fun setButtonClick() {
        with(binding){
            btnAddPhoto.setOnClickListener { openGallery() }
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
            val myFile = uriToFile(selectedImg, this)

            photoFile = myFile

            binding.ivImage.setImageURI(selectedImg)
        }
    }

}