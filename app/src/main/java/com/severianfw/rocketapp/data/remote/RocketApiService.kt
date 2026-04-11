package com.severianfw.rocketapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RocketApiService {
    companion object {
        const val BASE_URL = "https://ll.thespacedevs.com/2.2.0/"
    }

    @GET("config/launcher/?manufacturer__name=SpaceX")
    suspend fun getRockets(): RocketListResponse

    @GET("config/launcher/{id}/")
    suspend fun getRocketDetail(@Path("id") id: Int): RocketDetailResponse

    @GET("launch/upcoming/")
    suspend fun getUpcomingLaunches(
        @Query("search") search: String = "SpaceX",
        @Query("limit") limit: Int = 5,
        @Query("mode") mode: String = "detailed"
    ): RocketLaunchesResponse
}
