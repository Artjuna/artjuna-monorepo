package com.artjuna.artjuna_app.core.data.source.local.preferences

import android.content.Context

class Preferences(private val context: Context){
    companion object {
        private const val PREFS_NAME = "INSTA_PREF"

        private const val USERNAME = "USERNAME"
        private const val EMAIL = "EMAIL"
        private const val PASSWORD = "PASSWORD"
        private const val TOKEN = "TOKEN"

    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

//    fun saveUser(user:User){
//        preferences.edit().apply{
//            putString(USERNAME, user.username)
//            putString(EMAIL, user.email)
//            putString(PASSWORD, user.password)
//            putString(TOKEN, user.token)
//        }.apply()
//    }
//
//    fun getUser():User{
//        return User(
//            username = preferences.getString(USERNAME,"")!!,
//            email = preferences.getString(EMAIL,"")!!,
//            password = preferences.getString(PASSWORD,"")!!,
//            token = preferences.getString(TOKEN,"")!!,
//        )
//    }
}