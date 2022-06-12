package com.artjuna.artjuna_app.core.data.source.remote.response

import android.os.Parcelable
import androidx.paging.PagingDataAdapter
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.utils.AppUtils
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StyleTransferResponse(

    @field:SerializedName("StylizedImage")
    val stylizedImage: String? = null
) : Parcelable



