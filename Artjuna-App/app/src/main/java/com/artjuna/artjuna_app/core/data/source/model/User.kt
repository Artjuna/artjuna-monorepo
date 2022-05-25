package com.artjuna.artjuna_app.core.data.source.model

data class User(
    var id:String="",
    var fullName:String="",
    var userName:String="",
    var email:String="",
    var password:String="",
    var isStore:Boolean=false,
    var followers:Int=0,
    var numberWA:String="",
    var city:String="",
    var isLogged:Boolean=false,
)
