package com.artjuna.artjuna_app.core.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artjuna.artjuna_app.core.data.source.local.LocalDataSource
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.core.data.source.remote.RemoteDataSource
import com.artjuna.artjuna_app.core.data.source.remote.request.AddAccountRequest
import com.artjuna.artjuna_app.core.data.source.remote.response.AccountResponse
import com.artjuna.artjuna_app.core.data.source.remote.response.toUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val local:LocalDataSource,
    private val remote:RemoteDataSource,
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

    fun checkUsernameAvail(user:User) {
        showLoading(true)
        db.collection("users").get()
            .addOnSuccessListener { result ->
                for (document in result){
                    if (document.data["UserName"] == user.userName){
                        Log.d(TAG, "addOnSuccessListener ${document.data["UserName"]}")
                        showError("Username already exist")
                        return@addOnSuccessListener
                    }
                }
                signUp(user)

            }
            .addOnFailureListener { e ->
                val errorMsg = e.message.toString()
                showError(errorMsg)
            }
    }

    private fun signUp(user: User) {
        showLoading(true)
        Log.d(TAG, "signUp")
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    val fUser = auth.currentUser
                    if(fUser!=null){
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(user.fullName)
                            .build()
                        fUser.updateProfile(profileUpdates)

                        addAccountToAPI(user)
                    }

                }
                else{
                    val errorMsg = task.exception.toString()
                    showError(errorMsg)
                }
            }
    }

    private fun addAccountToAPI(user: User){
        showLoading(true)
        Log.d(TAG, "addAccountToAPI")
        val request = AddAccountRequest(
            Email = user.email,
            UserName = user.userName,
            FullName = user.fullName,
        )
        remote.addAccount(request).enqueue(object :Callback<AccountResponse>{
            override fun onResponse(
                call: Call<AccountResponse>,
                response: Response<AccountResponse>
            ) {
                if(response.isSuccessful){
                    val res = response.body()?.toUser()
                    saveUserToFirebase(res!!)
                    saveUserToLocal(res)
                    showSuccess("Sign Up Success")
                }else{
                    val errorMsg = response.errorBody()!!.string()
                    showError(errorMsg)
                }
            }

            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                val errorMsg = t.message.toString()
                showError(errorMsg)
            }
        })
    }


    private fun saveUserToLocal(user: User) {
        local.saveUser(user)
    }

    private fun saveUserToFirebase(user: User) {
        val userData = hashMapOf(
            "UserID" to user.id,
            "Email" to user.email,
            "UserName" to user.userName,
            "FullName" to user.fullName
        )
        db.collection("users").document(user.email).set(userData)
    }

    fun signIn(email:String,password:String) {
        showLoading(true)
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val fUser = auth.currentUser
                    if(fUser!=null){
                        getUserFromFirebaseByEmail(fUser.email!!)
                    }
                }else{
                    val errorMsg = it.exception.toString()
                    showError(errorMsg)
                }
            }
    }

    private fun getUserFromFirebaseByEmail(email: String){
        showLoading(true)
        db.collection("users").document(email)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    val userId = document["UserID"].toString()
                    getAccountByIdFromAPI(userId)

                } else {
                    signOut()
                    val errorMsg = "Try Again"
                    showError(errorMsg)
                }
            }
    }

    private fun getAccountByIdFromAPI(id:String){
        showLoading(true)
        remote.getAccountById(id).enqueue(object :retrofit2.Callback<List<AccountResponse>>{
            override fun onResponse(
                call: Call<List<AccountResponse>>,
                response: Response<List<AccountResponse>>
            ) {
                if(response.isSuccessful){
                    val res = response.body()!![0].toUser()
                    showSuccess("Success Sign In")
                    saveUserToLocal(res)
                }else{
                    signOut()
                    val errorMsg = "Try Again"
                    showError(errorMsg)
                }
            }

            override fun onFailure(call: Call<List<AccountResponse>>, t: Throwable) {
                signOut()
                showError(t.message.toString())
            }

        })
    }

    fun signOut(){
        if(auth.currentUser!= null){
            auth.signOut()
            _isLogged.postValue(false)
            local.saveUser(User())
            local.setAddress(Address())
        }
    }

    fun getLoginStatus():LiveData<Boolean>{
        val logged = MutableLiveData<Boolean>()
        if(auth.currentUser!=null){
            logged.postValue(true)
        }else{
            logged.postValue(false)
        }
        return logged
    }

    private fun showLoading(loading:Boolean){
        _isLoading.postValue(loading)
    }

    private fun showError(msg: String) {
        _isLoading.postValue(false)
        _isLogged.postValue(false)
        _message.postValue(msg)
        Log.d(TAG, msg)
    }

    private fun showSuccess(msg: String) {
        _isLoading.postValue(false)
        _isLogged.postValue(true)
        _message.postValue(msg)
    }
}