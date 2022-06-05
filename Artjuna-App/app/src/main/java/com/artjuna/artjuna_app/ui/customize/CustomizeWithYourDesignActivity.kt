package com.artjuna.artjuna_app.ui.customize

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivityCustomizeWithYouDesignBinding
import com.artjuna.artjuna_app.utils.AppUtils
import java.io.File

class CustomizeWithYourDesignActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCustomizeWithYouDesignBinding
    private var photoFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizeWithYouDesignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()

    }

    private fun setButton(){
        with(binding){
            btnAddPhoto.setOnClickListener { openGallery() }
            bottombar.btnCheckout.setOnClickListener {  }
        }
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


}