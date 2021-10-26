package com.technipixl.filrouge.network.models


import com.google.gson.annotations.SerializedName
import java.util.*

data class MoveBusinessModel(
    @SerializedName("alias")
    val alias: String?,
    @SerializedName("categories")
    val categories: List<Locale.Category>?,
    @SerializedName("coordinates")
    val coordinates: CoordinatesModel?,
    @SerializedName("display_phone")
    val displayPhone: String?,
    @SerializedName("distance")
    val distance: Double?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("is_closed")
    val isClosed: Boolean?,
    @SerializedName("location")
    val location: LocationModel?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("review_count")
    val reviewCount: Int?,
    @SerializedName("transactions")
    val transactions: List<Any>?,
    @SerializedName("url")
    val url: String?
)