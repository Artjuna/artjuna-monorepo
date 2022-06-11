package com.artjuna.artjuna_app.core.data.source.remote.response

import com.artjuna.artjuna_app.core.data.source.model.Order
import com.artjuna.artjuna_app.utils.AppUtils
import com.google.gson.annotations.SerializedName

data class GetOrderResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("ProductName")
	val productName: String,

	@field:SerializedName("BuyerUserID")
	val buyerUserID: String,

	@field:SerializedName("BuyerPhoneNumber")
	val buyerPhoneNumber: String,

	@field:SerializedName("TotalPrice")
	val totalPrice: Int,

	@field:SerializedName("ShippingAddress")
	val shippingAddress: String,

	@field:SerializedName("ProductID")
	val productID: String,

	@field:SerializedName("OrderID")
	val orderID: String,

	@field:SerializedName("BuyerFullName")
	val buyerFullName: String,

	@field:SerializedName("Image")
	val image: String,

	@field:SerializedName("SellerFullName")
	val sellerFullName: String,

	@field:SerializedName("SellerUserID")
	val sellerUserID: String
)

fun GetOrderResponse.toOrder():Order{
	return Order(
		id=orderID,
		storeId = sellerUserID,
		storeName = sellerFullName,
		buyerId = buyerUserID,
		buyerName = buyerFullName,
		buyerNumber = buyerPhoneNumber,
		buyerAddress = shippingAddress,
		productId = productID,
		productName = productName,
		price = totalPrice,
		date = createdAt,
		image = image
	)
}
