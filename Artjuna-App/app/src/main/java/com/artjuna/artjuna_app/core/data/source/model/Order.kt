package com.artjuna.artjuna_app.core.data.source.model

data class Order(
    var id:String="",
    var status:Int=0,
    var date:String="",
    var product: Product?=null
)
