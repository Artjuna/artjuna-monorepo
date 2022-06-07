package com.artjuna.artjuna_app.core.data.source.model

import com.artjuna.artjuna_app.core.data.source.remote.request.AddOrderRequest

data class Order(
    var id:String="",
    var storeId:String="",
    var storeName:String="",
    var buyerId:String="",
    var buyerName:String="",
    var buyerNumber:String="",
    var buyerAddress:String="",
    var productId:String="",
    var productName:String="",
    var price:Int=0,
    var date:String="",
    var image:String=""
)

fun Order.toAddOrderRequest():AddOrderRequest{
    return AddOrderRequest(
        ProductID = productId,
        BuyerUserID = buyerId,
        SellerUserID = storeId,
        BuyerPhoneNumber = buyerNumber,
        TotalPrice = price,
        ShippingAddress = buyerAddress
    )
}
