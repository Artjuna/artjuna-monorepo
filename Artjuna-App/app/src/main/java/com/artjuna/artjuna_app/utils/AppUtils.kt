package com.artjuna.artjuna_app.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.Html
import android.text.Spanned
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.artjuna.artjuna_app.BuildConfig
import com.artjuna.artjuna_app.R
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.bumptech.glide.Glide
import java.io.*
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

object AppUtils{

    fun saveImage(context: Context,bitmap: Bitmap, fileName:String):Boolean{
        val fos:OutputStream
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                val resolver = context.contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.jpg")
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+File.separator+"Artjuna")
                val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!) as FileOutputStream
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                Objects.requireNonNull(fos)
                return true
            }
        }catch (e:Exception){
            Log.d("GALIH", e.message.toString())
            return false
        }
        return false
    }

    fun sendOrderToWA(context: Context, number: String, product: Product, address: Address){
        val message = "Order Invoice\n" +
                "Item Name: ${product.name} (${product.id})\n" +
                "Price: Rp ${product.price}\n" +
                "Shipping Address :\n" +
                "${address.name} (${address.number})\n"+
                "${address.address} ${address.postalCode}"
        val url = "https://wa.me/${number}?text=${URLEncoder.encode(message,"UTF-8")}"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

    fun ImageView.loadImage(imageSource : String?) {
        Glide.with(context)
            .load(imageSource)
            .placeholder(R.drawable.ic_placeholder_image)
            .error(R.drawable.ic_placeholder_error)
            .into(this)
    }
    fun ImageView.loadImage(imageSource : ByteArray?) {
        Glide.with(context)
            .load(imageSource)
            .placeholder(R.drawable.ic_placeholder_image)
            .error(R.drawable.ic_placeholder_error)
            .into(this)
    }

    fun ImageView.loadImage(imageSource : Uri?) {
        Glide.with(context)
            .load(imageSource)
            .placeholder(R.drawable.ic_placeholder_image)
            .error(R.drawable.ic_placeholder_error)
            .into(this)
    }

    fun ImageView.loadImage(imageSource : Bitmap?) {
        Glide.with(context)
            .load(imageSource)
            .placeholder(R.drawable.ic_placeholder_image)
            .error(R.drawable.ic_placeholder_error)
            .into(this)
    }

    fun getImageSizeInKB(image:File):Int{
        return (image.length()/1024).toInt()
    }

    fun getProductImageURL(path:String):String = "${BuildConfig.BASE_URL}ProductImages${path}"

    fun getPostImageURL(path:String):String = "${BuildConfig.BASE_URL}PostImages${path}"


    fun getUserandCaption(username:String, caption:String): Spanned {
        return Html.fromHtml("<b>$username</b> $caption")
    }

    fun getLikedBy(liked:Int): Spanned {
        return Html.fromHtml("Liked by <b>$liked people</b>")
    }

    fun showToast(context:Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun convertImageToBase64(file: File): String {
        val bm = BitmapFactory.decodeFile(file.path)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 75, baos) // bm is the bitmap object
        val b: ByteArray = baos.toByteArray()
        val base64 = "data:image/jpeg;base64,${Base64.encodeToString(b, Base64.DEFAULT)}"
        return base64
    }

    fun convertBase64toByteArray(base64: String): ByteArray {
        return Base64.decode(base64, Base64.DEFAULT)
    }

    fun convertBase64toBitmap(base64Image: String):Bitmap{
        val imageByteArray = Base64.decode(base64Image,Base64.DEFAULT)
        val bitmapImage = BitmapFactory.decodeByteArray(imageByteArray,0, imageByteArray.size)
        return bitmapImage
    }

    //Get Image from Gallery
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

    fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte =
                Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: java.lang.Exception) {
            e.message
            null
        }
    }




}