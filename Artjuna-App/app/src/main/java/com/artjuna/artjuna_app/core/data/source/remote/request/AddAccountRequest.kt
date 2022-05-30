package com.artjuna.artjuna_app.core.data.source.remote.request

data class AddAccountRequest (
    var Email:String="",
    var UserName:String="",
    var FullName:String="",
    var OriginProvince:String="",
    var OriginCity:String="",
    var Telephone:String=""
)