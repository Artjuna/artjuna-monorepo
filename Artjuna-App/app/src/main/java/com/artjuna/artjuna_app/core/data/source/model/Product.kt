package com.artjuna.artjuna_app.core.data.source.model

data class Product(
    var id:String="",
    var name:String="",
    var image:String="",
    var price:Int=0,
    var city:String="",
    var rating:Double=0.0,
    var sold:Int=0
)
