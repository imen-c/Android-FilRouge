package com.technipixl.filrouge.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusinessModel(
    @SerializedName("alias")
    val alias: String,
    @SerializedName("categories")
    val categoryModels: List<CategoryModel>,
    @SerializedName("coordinates")
    val coordinatesModel: CoordinatesModel,
    @SerializedName("display_phone")
    val displayPhone: String,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("is_closed")
    val isClosed: Boolean,
    @SerializedName("location")
    val locationModel: LocationModel,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("review_count")
    val reviewCount: Int,
    @SerializedName("url")
    val url: String
): Parcelable