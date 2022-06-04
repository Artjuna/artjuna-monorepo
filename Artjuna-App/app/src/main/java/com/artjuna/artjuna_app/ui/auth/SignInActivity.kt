package com.artjuna.artjuna_app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.system.Os.accept
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.R
import com.artjuna.artjuna_app.databinding.ActivitySignInBinding
import com.artjuna.artjuna_app.ui.loading.LoadingDialog
import com.artjuna.artjuna_app.ui.navigation.NavigationActivity
import com.artjuna.artjuna_app.utils.AppUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.NonCancellable.cancel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel:AuthViewModel by viewModel()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLoading()
        setButtonClick()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLogged.observe(this){
            if(it){
                startActivity(Intent(this, NavigationActivity::class.java))
                finishAffinity()
            }
        }
        viewModel.message.observe(this){
            AppUtils.showToast(this, it)
        }
        viewModel.isLoading.observe(this){ loading ->
            if (loading) loadingDialog.show() else loadingDialog.dismiss()
        }
    }

    private fun setButtonClick() {

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener { signIn() }

        binding.btnForgot.setOnClickListener { forgotPassword() }
    }

    private fun forgotPassword() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Forgot Password?")
            .setMessage("Relax, try to remember your password!")
            .setPositiveButton("Thanks") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun signIn() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        if(formNotEmpty(email, password)){
            viewModel.signIn(email, password)
        }

    }

    private fun formNotEmpty(email:String, password:String): Boolean {
        if(email.isEmpty()){
            binding.etEmail.error = "Please enter this field"
            return false
        }
        if(password.isEmpty()){
            binding.etPassword.error = "Please enter this field"
            return false
        }
        return true
    }

    private fun setupLoading() {
        loadingDialog = LoadingDialog(this,false)
    }

    override fun onPause() {
        super.onPause()
        viewModel.message.removeObservers(this)
        viewModel.isLoading.removeObservers(this)
        viewModel.isLogged.removeObservers(this)
    }


}