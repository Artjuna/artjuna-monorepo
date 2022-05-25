package com.artjuna.artjuna_app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivitySignInBinding
import com.artjuna.artjuna_app.ui.navigation.NavigationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    companion object{
        var TAG = SignInActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if(email.isEmpty()){
            binding.etEmail.error = "Please enter this field"
            return
        }
        if(password.isEmpty()){
            binding.etPassword.error = "Please enter this field"
            return
        }

        binding.btnSignIn.visibility = View.INVISIBLE

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    Log.d(TAG, "signInWithEmail:success")
                    val intent = Intent(this@SignInActivity, NavigationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, " Authentication failed", Toast.LENGTH_SHORT).show()
                    binding.btnSignIn.visibility = View.VISIBLE
                }
            }


    }














}