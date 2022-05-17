package com.artjuna.artjuna_app.utils

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

object AppUtils{
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