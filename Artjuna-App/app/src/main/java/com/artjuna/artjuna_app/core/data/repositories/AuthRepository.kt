package com.artjuna.artjuna_app.core.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artjuna.artjuna_app.core.data.source.local.LocalDataSource
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.core.data.source.model.toUpdateRequest
import com.artjuna.artjuna_app.core.data.source.remote.RemoteDataSource
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.core.data.source.remote.request.AddAccountRequest
import com.artjuna.artjuna_app.core.data.source.remote.response.AccountResponse
import com.artjuna.artjuna_app.core.data.source.remote.response.toUser
import com.artjuna.artjuna_app.utils.AppExecutors
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
    private val db:FirebaseFirestore,
    private val appExecutors: AppExecutors
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
                        showError("Username ${document.data["UserName"]} already exist")
                        return@addOnSuccessListener
                    }
                }
                signUp(user)
            }
            .addOnFailureListener { e ->
                showError(e.message.toString())
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

                        addAccountToAPI(user)
                    }
                }
                else{
                    showError(task.exception.toString())
                }
            }
    }

    private fun addAccountToAPI(user: User){
        showLoading(true)
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
                    showError(response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                showError(t.message.toString())
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
                    showError(it.exception.toString())
                }
            }
    }

    private fun getUserFromFirebaseByEmail(email: String){
        showLoading(true)
        db.collection("users").document(email)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val userId = document["UserID"].toString()
                    getAccountByIdFromAPI(userId)
                } else {
                    signOut()
                    showError("Try Again")
                }
            }
            .addOnFailureListener{e ->
                signOut()
                showError(e.message.toString())
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
                    saveUserToLocal(res)
                    showSuccess("Success Sign In")
                }else{
                    signOut()
                    showError(response.errorBody()!!.string())
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
            appExecutors.diskIO().execute {
                local.signOut()
            }
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

    fun updateAccount(user:User):LiveData<Result<String>>{
        val result = MutableLiveData<Result<String>>()
        result.postValue(Result.Loading)
        remote.updateAccount(user.toUpdateRequest()).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    result.postValue(Result.Success("Success"))
                    local.saveUser(user)
                }else{
                    result.postValue(Result.Error(response.errorBody().toString()))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                result.postValue(Result.Error(t.message.toString()))
            }
        })
        return result
    }

    fun upgradeAccount(user:User):LiveData<Result<String>>{
        val result = MutableLiveData<Result<String>>()
        result.postValue(Result.Loading)
        user.isStore = true
        remote.updateAccount(user.toUpdateRequest()).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    result.postValue(Result.Success("Congrats, you've become a seller"))
                    local.saveUser(user)
                }else{
                    result.postValue(Result.Error(response.errorBody().toString()))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                result.postValue(Result.Error(t.message.toString()))
            }
        })
        return result
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