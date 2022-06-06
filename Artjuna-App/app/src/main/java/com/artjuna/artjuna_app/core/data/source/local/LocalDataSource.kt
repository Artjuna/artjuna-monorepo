package com.artjuna.artjuna_app.core.data.source.local

import com.artjuna.artjuna_app.core.data.source.local.entity.PostEntity
import com.artjuna.artjuna_app.core.data.source.local.entity.ProductEntity
import com.artjuna.artjuna_app.core.data.source.local.preferences.Preferences
import com.artjuna.artjuna_app.core.data.source.local.room.PostDao
import com.artjuna.artjuna_app.core.data.source.local.room.ProductDao
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.User

class LocalDataSource (
    private val pref:Preferences,
    private val productDao:ProductDao,
    private val postDao: PostDao
) {

    fun setAddress(address: Address){
        pref.saveAddress(address)
    }

    fun getAddress():Address = pref.getAddress()

    fun saveUser(user: User){
        pref.saveUser(user)
    }

    fun signOut(){
        productDao.deleteAllData()
        postDao.deleteAllData()
        saveUser(User())
        setAddress(Address())
    }

    fun getUser(): User = pref.getUser()

    fun insertProductToCart(productEntity: ProductEntity) = productDao.insertProductToCart(productEntity)

    fun deleteProductFromCartById(id:String) = productDao.deleteProductInCartById(id)

    fun getProductInCartById(id:String) = productDao.getProductInCartById(id)

    fun getAllProductInCart() = productDao.getAllProductInCart()


    fun insertPostLiked(post:PostEntity) = postDao.insertPostLiked(post)

    fun deletePostLikedById(id:String) = postDao.deletePostLikedById(id)

    fun getPostLikedById(id:String) = postDao.getPostLikedById(id)

    fun getAllPostLiked() = postDao.getAllPostLiked()

    fun getAllPostLikedId() = postDao.getAllPostLikedId()
}