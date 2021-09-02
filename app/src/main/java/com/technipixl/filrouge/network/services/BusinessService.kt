package com.technipixl.filrouge.network.services

import com.technipixl.filrouge.network.models.BusinessResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BusinessService {
    @Headers(
        "Content-type: application/json",
        "Authorization: Bearer 0ObDXEO7lfSJnJn_MUZgJOLBXYGihf3q0ojiOSxbgNa9r2skGNpuA2PsktUXcGFzWOM_DKRpKqbXEO6GCZNUUFJjgedZ7eesTW7Wb7AaaTZ2F5JRf8IygsssSj35YHYx"
    )
    @GET("businesses/search")
    suspend fun search(
        @Query("term", encoded = false) term: String,
        @Query("latitude", encoded = false) latitude: Double,
        @Query("longitude", encoded = false) longitude: Double,
        @Query("limit", encoded = false) limit: Int
    ): Response<BusinessResponse>
}