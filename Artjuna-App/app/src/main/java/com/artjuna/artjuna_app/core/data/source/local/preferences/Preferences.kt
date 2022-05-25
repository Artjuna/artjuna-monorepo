package com.artjuna.artjuna_app.core.data.source.local.preferences

import android.content.Context
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.User

class Preferences(private val context: Context){
    companion object {
        private const val PREFS_NAME = "INSTA_PREF"

        private const val ID = "ID"
        private const val FULLNAME = "FULLNAME"
        private const val USERNAME = "USERNAME"
        private const val EMAIL = "EMAIL"
        private const val ISSTORE = "ISSTORE"
        private const val FOLLOWERS = "FOLLOWERS"
        private const val NUMBERWA = "NUMBERWA"
        private const val CITY = "CITY"
        private const val IS_LOGGED = "IS_LOGGED"

        private const val ADR_NAME="ADR_NAME"
        private const val ADR_NUMBER="ADR_NUMBER"
        private const val ADR_ADDRESS="ADR_ADDRESS"
        private const val ADR_POSTALCODE="ADR_POSTALCODE"

    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUser(user: User){
        preferences.edit().apply{
            putString(ID,user.id)
            putString(FULLNAME,user.fullName)
            putString(USERNAME,user.userName)
            putString(EMAIL,user.email)
            putBoolean(ISSTORE,user.isStore)
            putInt(FOLLOWERS,user.followers)
            putString(NUMBERWA,user.numberWA)
            putString(CITY,user.city)
            putBoolean(IS_LOGGED,user.isLogged)
        }.apply()
    }

    fun getUser():User{
        return User(
            id = preferences.getString(ID,"")!!,
            fullName = preferences.getString(ID,"")!!,
            userName = preferences.getString(ID,"")!!,
            email = preferences.getString(ID,"")!!,
            isStore = preferences.getBoolean(ID,false),
            followers = preferences.getInt(ID,0),
            numberWA = preferences.getString(ID,"")!!,
            city = preferences.getString(ID,"")!!,
            isLogged = preferences.getBoolean(IS_LOGGED,false),
        )
    }

    fun saveAddress(address: Address){
        preferences.edit().apply(){
            putString(ADR_NAME, address.name)
            putString(ADR_NUMBER, address.number)
            putString(ADR_ADDRESS, address.address)
            putString(ADR_POSTALCODE, address.postalCode)
        }.apply()
    }

    fun getAddress():Address{
        return Address(
            name = preferences.getString(ADR_NAME,"")!!,
            number = preferences.getString(ADR_NUMBER,"")!!,
            address = preferences.getString(ADR_ADDRESS,"")!!,
            postalCode = preferences.getString(ADR_POSTALCODE,"")!!,
        )
    }
}