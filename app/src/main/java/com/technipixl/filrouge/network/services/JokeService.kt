package com.technipixl.filrouge.network.services

import com.technipixl.filrouge.network.models.JokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface JokeService {
    @Headers("Content-type: application/json")
    @GET("jokes/random/{count}")
    suspend fun jokes(@Path("count", encoded = false) count: Int): Response<JokeResponse>
}