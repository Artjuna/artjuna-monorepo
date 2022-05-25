package com.artjuna.artjuna_app.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.artjuna.artjuna_app.databinding.ActivitySplashBinding
import com.artjuna.artjuna_app.ui.auth.SignUpActivity
import com.artjuna.artjuna_app.ui.navigation.NavigationActivity
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        disableNightMode()
        moveNextPage()
    }

    private fun moveNextPage() {
        val delayTime = 2000L
        val currentUser =auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (currentUser == null){
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, NavigationActivity::class.java))
                finish()
            }
        }, delayTime)
    }

    private fun disableNightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }



}