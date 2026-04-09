package com.severianfw.rocketapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface RocketApiService {
    companion object {
        const val BASE_URL = "https://ll.thespacedevs.com/2.2.0/"
    }

    @GET("config/launcher/?manufacturer__name=SpaceX")
    suspend fun getRockets(): RocketListResponse

    @GET("config/launcher/{id}/")
    suspend fun getRocketDetail(@Path("id") id: Int): RocketDetailResponse
}
