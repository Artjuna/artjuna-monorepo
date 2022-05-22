package com.artjuna.artjuna_app.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import android.widget.Toast
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.bumptech.glide.Glide
import java.net.URLEncoder

object AppUtils{

    fun sendOrderToWA(context: Context, number: String, product: Product){
        val message = "Order Invoice\n" +
                "Item Name: ${product.name} (${product.id})\n" +
                "Price: Rp ${product.price}\n" +
                "Order ID: 232329"
        val url = "https://wa.me/${number}?text=${URLEncoder.encode(message,"UTF-8")}"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

    fun ImageView.loadImage(imageSource : String?) {
        Glide.with(context)
            .load(imageSource)
            .into(this)
    }

    fun getUserandCaption(username:String, caption:String): Spanned {
        return Html.fromHtml("<b>$username</b> $caption")
    }

    fun getLikedBy(liked:Int): Spanned {
        return Html.fromHtml("Liked by <b>$liked people</b>")
    }

    fun showToast(context:Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}