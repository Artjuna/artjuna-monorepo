package com.artjuna.artjuna_app.core.data.source.remote.network

import com.artjuna.artjuna_app.core.data.source.remote.request.AddAccountRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadPostRequest
import com.artjuna.artjuna_app.core.data.source.remote.response.AccountResponse
import com.artjuna.artjuna_app.core.data.source.remote.response.GetPostResponse
import com.artjuna.artjuna_app.core.data.source.remote.response.GetProductResponse
import com.artjuna.artjuna_app.core.data.source.remote.response.UploadPostResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product")
    suspend fun getProduct():Response<List<GetProductResponse>>

    @GET("Post/getPost")
    suspend fun getPost():Response<List<GetPostResponse>>

//    @GET("post")
//    suspend fun getPost():Response<List<GetPostResponse>>

    @POST("Post/addPost")
    suspend fun uploadPost(
        @Body body: UploadPostRequest
    ) : Response<UploadPostResponse>

    @POST("Account/addAccount")
    fun addAccount(
        @Body body:AddAccountRequest
    ):Call<AccountResponse>

//    @GET("Account/getAccountByUserID/{UserID}")
//    fun getAccountById(
//        @Path("UserID") id:String
//    ):Call<AccountResponse>

    @GET("Account/getAccountByUserID")
    fun getAccountById(
        @Query("UserID") userId:String
    ):Call<AccountResponse>



//    @POST("post")
//    suspend fun uploadPost(
//        @Body body: UploadPostRequest
//    ) : Response<UploadPostResponse>



}