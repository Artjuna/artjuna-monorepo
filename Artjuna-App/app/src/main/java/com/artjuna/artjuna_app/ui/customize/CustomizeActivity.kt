package com.artjuna.artjuna_app.ui.customize

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivityCustomizeBinding

class CustomizeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCustomizeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}