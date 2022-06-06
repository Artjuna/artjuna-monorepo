package com.artjuna.artjuna_app.ui.profilesettings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityProfileSettingsBinding
import com.artjuna.artjuna_app.ui.auth.SignInActivity
import com.artjuna.artjuna_app.ui.loading.LoadingDialog
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileSettingsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProfileSettingsBinding
    private lateinit var loadingDialog: LoadingDialog

    private val viewModel:ProfileSettingsViewModel by viewModel()
    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLoading()
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
        viewModel.updateAccount(this.user).observe(this){
            when(it){
                is Result.Loading -> loadingDialog.show()
                is Result.Error -> {
                    loadingDialog.dismiss()
                    AppUtils.showToast(this, it.error)
                }
                is Result.Success -> {
                    loadingDialog.dismiss()
                    finish()
                    onBackPressed()
                }
            }
        }
    }

    private fun upgradeAccount() {
        viewModel.upgradeAccount(this.user).observe(this){
            when(it){
                is Result.Loading -> loadingDialog.show()
                is Result.Error -> {
                    loadingDialog.dismiss()
                    AppUtils.showToast(this, it.error)
                }
                is Result.Success -> {
                    loadingDialog.dismiss()
                    AppUtils.showToast(this,it.data)
                    finish()
                    onBackPressed()
                }
            }
        }
    }

    private fun collectUserData() {
        user.fullName = binding.etFullName.text.toString()
        if(user.isStore){
            user.city = binding.etCity.text.toString()
            user.province = binding.etProvince.text.toString()
            user.numberWA = binding.etPhoneNumber.text?.trim().toString()
        }
    }

    private fun populateView() {
        with(binding){
            setViewStore()
            etUserId.setText(user.id)
            etFullName.setText(user.fullName)
            etUsername.setText(user.userName)
            etEmail.setText(user.email)
            etPhoneNumber.setText(user.numberWA)
            etCity.setText(user.city)
            etProvince.setText(user.province)
        }
    }

    private fun setViewStore() {
        with(binding){
            etPhoneNumber.visibility = if (user.isStore) View.VISIBLE else View.GONE
            etCity.visibility = if (user.isStore) View.VISIBLE else View.GONE
            etProvince.visibility = if (user.isStore) View.VISIBLE else View.GONE
            btnBeSeller.visibility = if (user.isStore) View.GONE else View.VISIBLE
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
            btnBeSeller.setOnClickListener { upgradeAccount() }
        }
    }



    private fun moveToSignIn() {
        startActivity(Intent(this@ProfileSettingsActivity, SignInActivity::class.java))
        finishAffinity()
    }

    private fun setupLoading() {
        loadingDialog = LoadingDialog(this,false)
    }

    companion object{
        const val EXTRA_USER = "EXTRA_USER"
    }
}