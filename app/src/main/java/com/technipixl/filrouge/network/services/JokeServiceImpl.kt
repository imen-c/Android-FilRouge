package com.technipixl.filrouge.network.services

import com.technipixl.filrouge.network.models.JokeResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class JokeServiceImpl: BaseServiceImpl() {

    companion object {
        private const val count: Int = 20
    }

    init {
        baseUrl = "https://api.icndb.com/"
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

    suspend fun getJokes(): Response<JokeResponse> = getRetrofit().create(JokeService::class.java).jokes(count)
}