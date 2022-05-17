package com.artjuna.artjuna_app.utils

import android.text.Html
import android.text.Spanned
import android.widget.ImageView
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

}