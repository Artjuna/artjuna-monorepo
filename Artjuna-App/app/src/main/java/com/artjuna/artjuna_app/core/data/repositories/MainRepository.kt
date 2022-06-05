package com.artjuna.artjuna_app.core.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.artjuna.artjuna_app.core.data.source.local.LocalDataSource
import com.artjuna.artjuna_app.core.data.source.local.entity.toProduct
import com.artjuna.artjuna_app.core.data.source.model.*
import com.artjuna.artjuna_app.core.data.source.remote.RemoteDataSource
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.core.data.source.remote.request.AddHasSeenRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadPostRequest
import com.artjuna.artjuna_app.core.data.source.remote.response.toPost
import com.artjuna.artjuna_app.core.data.source.remote.response.toProduct
import com.artjuna.artjuna_app.utils.AppExecutors
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MainRepository(
    private val local:LocalDataSource,
    private val remote:RemoteDataSource,
    private val appExecutors: AppExecutors
) {


    fun setAddress(address: Address) = local.setAddress(address)
    fun getAddress():Address = local.getAddress()

    fun getUser(): User = local.getUser()

    fun addHasSeen(productId:String):LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val userId = local.getUser().id
            remote.addHasSeen(AddHasSeenRequest(userId,productId)).let {
                if(it.isSuccessful){
                    emit(Result.Success(""))
                }else {
                    emit(Result.Error(it.errorBody().toString() ?: "Default error dongs"))
                }
            }
        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun getCategory():LiveData<Result<List<String>>> = liveData {
        emit(Result.Loading)
        try {

            remote.getCategory().let {
                if(it.isSuccessful){
                    val body = it.body()
                    val res = body?.map { it.category }
                    emit(Result.Success(res!!.toSet().toList()))
                }else {
                    emit(Result.Error(it.errorBody().toString() ?: "Default error dongs"))
                }
            }
        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun getProduct(size:Int):LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            remote.getProduct().let {
                if(it.isSuccessful){
                    val body = it.body()
                    val res = body?.map { it.toProduct() }
                    val list = res!!.take(size)
                    emit(Result.Success(list))
                }else {
                    emit(Result.Error(it.errorBody().toString() ?: "Default error dongs"))
                }
            }
        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun getProduct():LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            remote.getProduct().let {
                if(it.isSuccessful){
                    val body = it.body()
                    val res = body?.map { it.toProduct() }
                    emit(Result.Success(res!!))
                }else {
                    emit(Result.Error(it.errorBody().toString() ?: "Default error dongs"))
                }
            }
        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun getProductByCategory(category:String):LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            remote.getProductByCategory(category).let {
                if(it.isSuccessful){
                    val body = it.body()
                    val res = body?.map { it.toProduct() }
                    emit(Result.Success(res!!))
                }else {
                    emit(Result.Error(it.errorBody().toString() ?: "Default error dongs"))
                }
            }
        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun getProductByName(name:String):LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            remote.getProductByName(name).let {
                if(it.isSuccessful){
                    val body = it.body()
                    val res = body?.map { it.toProduct() }
                    emit(Result.Success(res!!))
                }else {
                    emit(Result.Error(it.errorBody().toString() ))
                }
            }
        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun getProductByUserId(userId:String):LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            //Temporary Take 10 of list
            remote.getProductByUserId(userId).let {
                if(it.isSuccessful){
                    val body = it.body()
                    val res = body?.map { it.toProduct() }
                    emit(Result.Success(res!!.take(10)))
                    Log.d("GALIH", "getProductByUserId $userId")
                }else {
                    emit(Result.Error(it.errorBody().toString() ))
                }
            }
        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun getPost():LiveData<Result<List<Post>>> = liveData {
        emit(Result.Loading)
        try {

            remote.getPost().let {
                if(it.isSuccessful){
                    val body = it.body()
                    val res = body?.map { it.toPost() }
                    emit(Result.Success(res!!))
                }else {
                    emit(Result.Error(it.errorBody().toString() ?: "Default error dongs"))
                }
            }
        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun uploadProduct(product: Product, image: File):LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val user = local.getUser()
            product.storeId = user.id
            product.storeCity = user.city
            product.storeProvince = user.province

            val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "Image",
                image.name,
                requestImageFile
            )

            remote.uploadProduct(product,imageMultipart).let {
                if (it.isSuccessful){
                    emit(Result.Success("Success"))
                }else{
                    emit(Result.Error(it.errorBody().toString()))
                }
            }

        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun uploadPost(post: Post,image: File):LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val userId = local.getUser().id
            post.userId = userId

            val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "Image",
                image.name,
                requestImageFile
            )

            remote.uploadPost(post,imageMultipart).let {
                if(it.isSuccessful){
                    emit(Result.Success("Success"))
                }else {
                    emit(Result.Error(it.errorBody().toString()))
                }
            }
        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun checkIfProductInCart(id: String):LiveData<Boolean>{
        val isInCart = MutableLiveData<Boolean>()
        appExecutors.diskIO().execute {
            val listProduct = local.getProductInCartById(id)
            listProduct.map {
                Log.d("GALIH", it.id)
                Log.d("GALIH", it.name)
            }
            isInCart.postValue(listProduct.isNotEmpty())
        }
        return isInCart
    }

    fun insertProductToCart(product: Product){
        appExecutors.diskIO().execute{
            local.insertProductToCart(product.toProductEntity())
        }
    }

    fun deleteProductFromCartById(id:String){
        appExecutors.diskIO().execute{
            local.deleteProductFromCartById(id)
        }
    }

    fun getAllProductInCart():LiveData<List<Product>>{
        val listProduct = MutableLiveData<List<Product>>()
        appExecutors.diskIO().execute {
            val res = local.getAllProductInCart()
            val productRes = res.map { it.toProduct() }
            listProduct.postValue(productRes)
        }
        return listProduct
    }


}