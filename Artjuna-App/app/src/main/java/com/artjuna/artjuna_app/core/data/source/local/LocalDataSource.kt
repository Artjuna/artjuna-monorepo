package com.artjuna.artjuna_app.core.data.source.local

import com.artjuna.artjuna_app.core.data.source.local.preferences.Preferences
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.User

class LocalDataSource (private val pref:Preferences) {

    fun setAddress(address: Address){
        pref.saveAddress(address)
    }

    fun getAddress():Address = pref.getAddress()

    fun saveUser(user: User){
        pref.saveUser(user)
    }

    fun getUser(): User = pref.getUser()

}