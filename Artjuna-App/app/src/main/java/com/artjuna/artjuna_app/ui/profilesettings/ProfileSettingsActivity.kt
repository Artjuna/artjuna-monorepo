package com.artjuna.artjuna_app.ui.profilesettings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityProfileSettingsBinding
import com.artjuna.artjuna_app.ui.auth.SignInActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileSettingsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProfileSettingsBinding
    private val viewModel:ProfileSettingsViewModel by viewModel()
    private var user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonClick()
        getData()
    }

    private fun getData() {
        val userRes = intent.extras?.getParcelable<User>(EXTRA_USER)
        user = userRes!!
        populateView()
    }

    private fun updateAccount() {
        collectUserData()
        Log.d("okhttp",this.user.id)
        Log.d("okhttp",this.user.fullName)
        viewModel.updateAccount(this.user).observe(this){
            when(it){
                is Result.Success -> {
                    finish()
                    onBackPressed()
                }
            }
        }
    }

    private fun collectUserData() {
        user.fullName = binding.etFullName.text.toString()
        user.city = binding.etCity.text.toString()
        user.numberWA = binding.etPhoneNumber.text.toString()
    }

    private fun populateView() {
        with(binding){
            etUserId.setText(user.id)
            etFullName.setText(user.fullName)
            etUsername.setText(user.userName)
            etEmail.setText(user.email)
            etPhoneNumber.setText(user.numberWA)
            etCity.setText(user.city)
        }
    }

    private fun setButtonClick() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
            btnSignOut.setOnClickListener {
                viewModel.signOut()
                moveToSignIn()
            }
            btnSave.setOnClickListener { updateAccount() }
        }
    }

    private fun moveToSignIn() {
        startActivity(Intent(this@ProfileSettingsActivity, SignInActivity::class.java))
        finishAffinity()
    }

    companion object{
        const val EXTRA_USER = "EXTRA_USER"
    }
}