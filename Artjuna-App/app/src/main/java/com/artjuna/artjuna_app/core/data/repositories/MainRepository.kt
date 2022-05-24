package com.artjuna.artjuna_app.core.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.artjuna.artjuna_app.core.data.source.local.LocalDataSource
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.core.data.source.remote.RemoteDataSource
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.core.data.source.remote.response.toPost
import com.artjuna.artjuna_app.core.data.source.remote.response.toProduct

class MainRepository(private val local:LocalDataSource, private val remote:RemoteDataSource) {


    fun setAddress(address: Address) = local.setAddress(address)
    fun getAddress():Address = local.getAddress()
    fun saveUser(user: User) = local.saveUser(user)
    fun getUser(): User =local.getUser()


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

}