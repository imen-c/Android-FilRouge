package com.technipixl.filrouge.network.models

import com.google.gson.annotations.SerializedName

data class JokeModel(
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("joke")
    val joke: String
)