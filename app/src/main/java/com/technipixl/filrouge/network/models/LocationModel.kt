package com.technipixl.filrouge.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationModel(
    @SerializedName("address1")
    val address1: String,
    @SerializedName("address2")
    val address2: String,
    @SerializedName("address3")
    val address3: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("display_address")
    val displayAddress: List<String>,
    @SerializedName("state")
    val state: String,
    @SerializedName("zip_code")
    val zipCode: String
): Parcelable