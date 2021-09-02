package com.technipixl.filrouge.network.services

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.technipixl.filrouge.network.models.BusinessResponse
import com.technipixl.filrouge.network.models.CoordinatesModel

class BusinessServiceImpl: BaseServiceImpl() {

    init {
        baseUrl = "https://api.yelp.com/v3/"
    }

    enum class SearchTerms(val value: String) {
        FOOD("food"),
        PARK("park")
    }

    companion object {
        private const val count: Int = 50
        val defaultCoordinates = CoordinatesModel(latitude = 50.59379456353284, longitude = 5.560027569777806)
    }

    private fun getRetrofit(): Retrofit {
        val okBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder.build())
            .build()
    }

    suspend fun search(term: String, latitude: Double, longitude: Double): Response<BusinessResponse> = getRetrofit().create(BusinessService::class.java).search(term, latitude, longitude, count)
}