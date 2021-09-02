package com.technipixl.filrouge.network.models

import com.google.gson.annotations.SerializedName

data class BusinessResponse(
    @SerializedName("businesses")
    val businesses: List<BusinessModel>,
    @SerializedName("region")
    val regionModel: RegionModel,
    @SerializedName("total")
    val total: Int
)