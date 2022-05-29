package com.artjuna.artjuna_app.ui.likedpost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivityLikedPostBinding

class LikedPostActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLikedPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}