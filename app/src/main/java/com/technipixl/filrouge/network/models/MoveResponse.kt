package com.technipixl.filrouge.network.models


import com.google.gson.annotations.SerializedName

data class MoveResponse(
    @SerializedName("businesses")
    val businesses: List<MoveBusinessModel>?,
    @SerializedName("region")
    val region: RegionModel?,
    @SerializedName("total")
    val total: Int?
)