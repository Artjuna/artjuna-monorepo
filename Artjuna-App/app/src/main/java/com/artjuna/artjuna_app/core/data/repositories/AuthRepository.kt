package com.artjuna.artjuna_app.core.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artjuna.artjuna_app.core.data.source.local.LocalDataSource
import com.artjuna.artjuna_app.core.data.source.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository(
    private val local:LocalDataSource,
    private val auth:FirebaseAuth,
    private val db:FirebaseFirestore
    ) {

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    val isLogged: LiveData<Boolean>
        get() = _isLogged
    private val _isLogged = MutableLiveData<Boolean>()

    val message: LiveData<String>
        get() = _message
    private val _message = MutableLiveData<String>()

    private val TAG = AuthRepository::class.java.simpleName


    fun signIn(email:String,password:String) {
        showLoading(true)
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val fUser = auth.currentUser
                    if(fUser!=null){
                        val user = User(
                            id = fUser.uid,
                            fullName = fUser.displayName!!,
                            email = fUser.email!!,
                            isLogged = true
                        )
                        local.saveUser(user)
                        showLoading(false)
                        showSuccess("Sign In Successful")
                    }
                }else{
                    showLoading(false)
                    showError(it.exception.toString())
                }
            }
    }

    fun checkUsernameAvail(user:User) {
        showLoading(true)
        db.collection("users").get()
            .addOnSuccessListener { result ->
                for (document in result){
                    if (document.data["username"] == user.userName){
                        showLoading(false)
                        Log.d(TAG, "addOnSuccessListener ${document.data["username"]}")
                        showError("Username already exist")
                        return@addOnSuccessListener
                    }
                }
                signUp(user)

            }
            .addOnFailureListener { e ->
                showError(e.message.toString())
                Log.d(TAG, "addOnFailureListener ${e.message.toString()}")
            }
    }

    private fun signUp(user: User) {
        showLoading(true)
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    val fUser = auth.currentUser
                    if(fUser!=null){
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(user.fullName)
                            .build()
                        fUser.updateProfile(profileUpdates)

                        val userData = hashMapOf(
                            "id" to fUser.uid,
                            "fullName" to user.fullName,
                            "username" to user.userName,
                            "email" to user.email
                        )
                        db.collection("users").document(fUser.uid).set(userData)

                        val pUser = User(
                            id = fUser.uid,
                            fullName = fUser.displayName!!,
                            userName = user.userName,
                            email = fUser.email!!,
                            isLogged = true
                        )
                        local.saveUser(pUser)

                        showLoading(false)
                        showSuccess("Success Register User")
                    }

                }
                else{
                    showLoading(false)
                    Log.w(TAG, "signUpWithEmail: failure", task.exception)
                    showError(task.exception.toString())
                }
            }
    }

    private fun showLoading(loading:Boolean){
        _isLoading.postValue(loading)
    }

    private fun showError(msg: String) {
        _isLogged.postValue(false)
        _message.postValue(msg)
    }

    private fun showSuccess(msg: String) {
        _isLogged.postValue(true)
        _message.postValue(msg)
    }
}