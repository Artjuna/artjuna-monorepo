package com.artjuna.artjuna_app.ui.resultcustomize

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivityResultCustomizeBinding

class ResultCustomizeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResultCustomizeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}