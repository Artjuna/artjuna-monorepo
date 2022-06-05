package com.artjuna.artjuna_app.core.data.source.model

import android.os.Parcelable
import com.artjuna.artjuna_app.core.data.source.remote.request.UpdateAccountRequest
import kotlinx.parcelize.Parcelize

@Parcelize
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
    var province:String="",
):Parcelable

fun User.toUpdateRequest():UpdateAccountRequest{
    return UpdateAccountRequest(
        UserID = id,
        FullName = fullName,
        UserName = userName,
        Email = email,
        OriginCity = city,
        OriginProvince = province,
        Telephone = numberWA,
        IsStore = isStore
    )
}
