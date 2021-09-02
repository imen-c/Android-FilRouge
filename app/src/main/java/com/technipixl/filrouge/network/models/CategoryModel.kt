package com.technipixl.filrouge.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    @SerializedName("alias")
    val alias: String,
    @SerializedName("title")
    val title: String
): Parcelable