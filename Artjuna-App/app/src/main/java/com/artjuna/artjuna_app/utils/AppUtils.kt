package com.artjuna.artjuna_app.utils

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import android.widget.Toast
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

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

    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    fun createTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }


    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

}