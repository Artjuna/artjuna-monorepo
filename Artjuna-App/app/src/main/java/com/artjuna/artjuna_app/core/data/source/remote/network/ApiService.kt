package com.artjuna.artjuna_app.core.data.source.remote.network

import com.artjuna.artjuna_app.core.data.source.remote.request.AddAccountRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UpdateAccountRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadPostRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadProductRequest
import com.artjuna.artjuna_app.core.data.source.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("Product/addProduct")
    suspend fun uploadProduct(
    @Part("UserID") userId : RequestBody,
    @Part("ProductName") productName : RequestBody,
    @Part("Category") category : RequestBody,
    @Part("Province") province : RequestBody,
    @Part("City") city : RequestBody,
    @Part("Description") caption : RequestBody,
    @Part("Price") price : Int,
    @Part image : MultipartBody.Part,
    ) : Response<Void>

    @GET("Product/getAllProduct")
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

    @PUT("Account/updateAccount")
    fun updateAccount(
        @Body body:UpdateAccountRequest
    ):Call<Void>



}