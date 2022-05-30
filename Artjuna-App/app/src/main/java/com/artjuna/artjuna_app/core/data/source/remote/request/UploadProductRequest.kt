package com.artjuna.artjuna_app.core.data.source.remote.request

data class UploadProductRequest (
    var UserID:String="",
    var ProductName:String="",
    var Image:String="",
    var Category:String="",
    var Province:String="",
    var City:String="",
    var Caption:String="",
    var Price:Int=0,
)