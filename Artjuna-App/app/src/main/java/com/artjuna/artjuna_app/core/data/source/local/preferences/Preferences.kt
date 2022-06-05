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
        private const val PROVINCE = "PROVINCE"

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
            putString(PROVINCE,user.province)
        }.apply()
    }

    fun getUser():User{
        return User(
            id = preferences.getString(ID,"")!!,
            fullName = preferences.getString(FULLNAME,"")!!,
            userName = preferences.getString(USERNAME,"")!!,
            email = preferences.getString(EMAIL,"")!!,
            isStore = preferences.getBoolean(ISSTORE,false),
            followers = preferences.getInt(FOLLOWERS,0),
            numberWA = preferences.getString(NUMBERWA,"")!!,
            city = preferences.getString(CITY,"")!!,
            province = preferences.getString(PROVINCE,"")!!
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