package com.artjuna.artjuna_app.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.databinding.ActivitySignUpBinding
import com.artjuna.artjuna_app.ui.loading.LoadingDialog
import com.artjuna.artjuna_app.ui.navigation.NavigationActivity
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    private val viewModel:AuthViewModel by viewModel()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLoading()
        setButtonClick()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLogged.observe(this){
            if(it){
                startActivity(Intent(this,NavigationActivity::class.java))
                finish()
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
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.btnSignUp.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        val fullName = binding.etFullName.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val user = User(fullName = fullName, userName = username, password = password, email = email)

        if(formNotEmpty(user)){
            viewModel.signUp(user)
        }
    }

    private fun formNotEmpty(user: User): Boolean {
        if(user.fullName.isEmpty()){
            binding.etFullName.error = "Please enter this field"
            return false
        }
        if(user.userName.isEmpty()){
            binding.etUsername.error = "Please enter this field"
            return false
        }
        if(user.email.isEmpty()){
            binding.etEmail.error = "Please enter this field"
            return false
        }
        if(user.password.isEmpty()){
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













