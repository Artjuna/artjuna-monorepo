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
import com.artjuna.artjuna_app.ui.navigation.NavigationActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    private val viewModel:SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        disableNightMode()
        setDelay()
    }

    private fun setDelay() {
        val delayTime = 2000L

        Handler(Looper.getMainLooper()).postDelayed({
            checkLoginStatus()
        }, delayTime)
    }

    private fun checkLoginStatus() {
        viewModel.getLoginStatus().observe(this){ logged ->
            if(logged){
                moveToHome()
            }else{
                moveToSignUp()
            }
        }
    }

    private fun moveToSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finishAffinity()
    }

    private fun moveToHome() {
        startActivity(Intent(this, NavigationActivity::class.java))
        finishAffinity()
    }


    private fun disableNightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }



}