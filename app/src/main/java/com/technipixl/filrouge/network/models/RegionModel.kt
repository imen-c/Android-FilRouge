package com.technipixl.filrouge.network.models

import com.google.gson.annotations.SerializedName

data class RegionModel(
    @SerializedName("center")
    val center: CoordinatesModel
)