package com.artjuna.artjuna_app.core.data.source.remote.request

data class AddOrderRequest (
    var ProductID:String="",
    var BuyerUserID:String="",
    var SellerUserID:String="",
    var BuyerPhoneNumber:String="",
    var TotalPrice:Int=0,
    var ShippingAddress:String=""
)