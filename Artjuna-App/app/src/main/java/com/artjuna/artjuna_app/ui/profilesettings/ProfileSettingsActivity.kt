package com.artjuna.artjuna_app.ui.profilesettings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivityProfileSettingsBinding
import com.artjuna.artjuna_app.ui.auth.SignInActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileSettingsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProfileSettingsBinding
    private val viewModel:ProfileSettingsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonClick()
    }

    private fun setButtonClick() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
            btnSignOut.setOnClickListener {
                viewModel.signOut()
                moveToSignIn()
            }
        }
    }

    private fun moveToSignIn() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}