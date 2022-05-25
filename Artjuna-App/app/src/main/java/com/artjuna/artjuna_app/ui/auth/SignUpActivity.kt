package com.artjuna.artjuna_app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivitySignUpBinding
import com.artjuna.artjuna_app.ui.navigation.NavigationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    companion object{
        var TAG = SignUpActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore

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

        if(fullName.isEmpty()){
            binding.etFullName.error = "Please enter this field"
            return
        }
        if(username.isEmpty()){
            binding.etUsername.error = "Please enter this field"
            return
        }
        if(email.isEmpty()){
            binding.etEmail.error = "Please enter this field"
            return
        }
        if(password.isEmpty()){
            binding.etPassword.error = "Please enter this field"
            return
        }

        binding.btnSignUp.visibility = View.INVISIBLE

        db.collection("users").get()
            .addOnSuccessListener { result ->
                for (document in result){
                    Log.d(TAG, "addOnSuccessListener ${document.data["username"]}")
                    if (document.data["username"] == username){
                        Log.d(TAG, "addOnSuccessListener ${document.data["username"]}")
                        binding.etUsername.error = "Username already exist"
                        Toast.makeText(this, "Username already exist", Toast.LENGTH_SHORT).show()
                        binding.btnSignUp.visibility =View.VISIBLE
                        return@addOnSuccessListener
                    }
                }
                userRegister(fullName, username, email, password)
            }
            .addOnFailureListener { e ->
                binding.btnSignUp.visibility = View.VISIBLE
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }

    }

    private fun userRegister(fullName: String, username: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(fullName)
                        .build()

                    user?.updateProfile(profileUpdates)

                    val userData = hashMapOf(
                        "fullName" to fullName,
                        "username" to username,
                        "email" to email
                    )

                    db.collection("users").add(userData)

                    val intent = Intent(this@SignUpActivity, NavigationActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                else{
                    Log.w(TAG, "signUpWithEmail: failure", task.exception)
                    binding.btnSignUp.visibility = View.VISIBLE
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }


    }
}













