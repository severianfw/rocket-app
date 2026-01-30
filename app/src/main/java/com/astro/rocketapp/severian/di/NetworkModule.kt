package com.astro.rocketapp.severian.di

import com.astro.rocketapp.severian.data.remote.RocketApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(RocketApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: RocketApiService by lazy {
        retrofit.create(RocketApiService::class.java)
    }
}
