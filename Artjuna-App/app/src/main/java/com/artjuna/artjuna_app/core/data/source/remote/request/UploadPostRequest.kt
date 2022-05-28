package com.artjuna.artjuna_app.core.data.source.remote.request

data class UploadPostRequest (
    var UserID:String="",
    var PostName:String="",
    var Caption:String="",
    var Category:String="",
    var Image:String=""
)