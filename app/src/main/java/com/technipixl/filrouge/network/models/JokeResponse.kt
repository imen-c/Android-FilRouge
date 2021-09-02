package com.technipixl.filrouge.network.models

import com.google.gson.annotations.SerializedName

data class JokeResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val jokes: List<JokeModel>
)