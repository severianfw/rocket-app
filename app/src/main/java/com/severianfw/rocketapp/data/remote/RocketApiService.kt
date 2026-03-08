package com.severianfw.rocketapp.data.remote

import retrofit2.http.GET

interface RocketApiService {
    companion object {
        const val BASE_URL = "https://api.spacexdata.com/v4/"
    }

    @GET("rockets")
    suspend fun getRockets(): List<RocketResponseItem>
}
