package com.artjuna.artjuna_app.core.data.source.remote.network

import com.artjuna.artjuna_app.core.data.source.remote.request.AddAccountRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadPostRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadProductRequest
import com.artjuna.artjuna_app.core.data.source.remote.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("Product/addProduct")
    suspend fun uploadProduct(
        @Body body: UploadProductRequest
    ) : Response<ProductResponse>

    @GET("product")
    suspend fun getProduct():Response<List<GetProductResponse>>

    @GET("Post/getPost")
    suspend fun getPost():Response<List<GetPostResponse>>

    @POST("Post/addPost")
    suspend fun uploadPost(
        @Body body: UploadPostRequest
    ) : Response<PostResponse>

    @POST("Account/addAccount")
    fun addAccount(
        @Body body:AddAccountRequest
    ):Call<AccountResponse>

    @GET("Account/getAccountByUserID/{UserID}")
    fun getAccountById(
        @Path("UserID") id:String
    ):Call<List<AccountResponse>>




}