package com.artjuna.artjuna_app.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.artjuna.artjuna_app.databinding.ActivitySplashBinding
import com.artjuna.artjuna_app.ui.auth.SignUpActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        disableNightMode()
        moveNextPage()
    }

    private fun moveNextPage() {
        val delayTime = 2000L

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }, delayTime)
    }

    private fun disableNightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}