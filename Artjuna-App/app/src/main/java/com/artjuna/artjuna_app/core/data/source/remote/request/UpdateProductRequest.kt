package com.artjuna.artjuna_app.core.data.source.remote.request

data class UpdateProductRequest (
    var ProductID:String="",
    var UserID:String="",
    var ProductName:String="",
    var Category:String="",
    var Province:String="",
    var City:String="",
    var Description:String="",
    var Price:Int=0,
)