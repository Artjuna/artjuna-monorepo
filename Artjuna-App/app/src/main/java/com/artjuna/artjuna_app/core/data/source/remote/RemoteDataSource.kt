package com.artjuna.artjuna_app.core.data.source.remote

import com.artjuna.artjuna_app.core.data.source.remote.network.ApiService
import com.artjuna.artjuna_app.core.data.source.remote.request.AddAccountRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadPostRequest
import com.artjuna.artjuna_app.core.data.source.remote.response.AccountResponse
import retrofit2.Call

class RemoteDataSource(private val api:ApiService) {

    //suspend fun getRegister() = api.getRegister()
    suspend fun getProduct() = api.getProduct()
    suspend fun getPost() = api.getPost()
    suspend fun uploadPost(request:UploadPostRequest) = api.uploadPost(request)

    fun addAccount(request:AddAccountRequest) = api.addAccount(request)
    fun getAccountById(userId:String): Call<List<AccountResponse>> = api.getAccountById(userId)

}