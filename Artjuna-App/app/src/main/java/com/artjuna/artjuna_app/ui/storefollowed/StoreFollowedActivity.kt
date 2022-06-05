package com.artjuna.artjuna_app.ui.storefollowed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.artjuna.artjuna_app.R
import com.artjuna.artjuna_app.databinding.ActivityStoreFollowedBinding

class StoreFollowedActivity : AppCompatActivity() {

    private lateinit var binding:ActivityStoreFollowedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreFollowedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}