package com.artjuna.artjuna_app.core.data.source.remote.network

import com.artjuna.artjuna_app.core.data.source.remote.request.*
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

    @Multipart
    @POST("Post/addPost")
    suspend fun uploadPost(
        @Part("UserID") userId : RequestBody,
        @Part("PostName") postName : RequestBody,
        @Part("Caption") caption : RequestBody,
        @Part Image : MultipartBody.Part,
    ) : Response<Void>

    @POST("Product/Seen")
    suspend fun addHasSeen(
        @Body body: AddHasSeenRequest
    ):Response<Void>

    @GET("Product/getProductFilter")
    suspend fun getProductByCategory(
        @Query("Category") category:String
    ): Response<List<ProductResponse>>

    @GET("Product/getProductFilter")
    suspend fun getProductByName(
        @Query("ProductName") name:String
    ): Response<List<ProductResponse>>

    @GET("Product/getProductFilter")
    suspend fun getProductByUserId(
        @Query("UserID") userId:String
    ): Response<List<ProductResponse>>


    @GET("Product/getAllProduct")
    suspend fun getProduct(
        @Query("page") page:Int,
        @Query("limit") limit:Int,
    ):Response<GetProductResponse>

    @GET("Product/getAllProductCategory")
    suspend fun getCategory():Response<List<GetCategoryResponse>>

    @GET("Post/getPost")
    suspend fun getPost():Response<List<GetPostResponse>>

    @PUT("Product/updateProduct")
    suspend fun updateProduct(
        @Body body: UpdateProductRequest
    ):Response<Void>


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