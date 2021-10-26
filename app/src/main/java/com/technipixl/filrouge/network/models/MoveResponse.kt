package com.technipixl.filrouge.network.models


import com.google.gson.annotations.SerializedName

data class MoveResponse(
    @SerializedName("businesses")
    val businesses: List<MoveBusinessModel>?,
    @SerializedName("region")
    val region: Region?,
    @SerializedName("total")
    val total: Int?
)