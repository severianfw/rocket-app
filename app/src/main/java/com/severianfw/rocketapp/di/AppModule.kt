package com.severianfw.rocketapp.di

import com.severianfw.rocketapp.data.repository.RocketRepositoryImpl
import com.severianfw.rocketapp.domain.repository.RocketRepository
import com.severianfw.rocketapp.domain.usecase.GetRocketDetailUseCase
import com.severianfw.rocketapp.domain.usecase.GetRocketsUseCase
import com.severianfw.rocketapp.domain.usecase.GetUpcomingLaunchesUseCase

object AppModule {

    val rocketRepository: RocketRepository by lazy {
        RocketRepositoryImpl(NetworkModule.apiService)
    }

    val getRocketsUseCase: GetRocketsUseCase by lazy {
        GetRocketsUseCase(rocketRepository)
    }

    val getRocketDetailUseCase: GetRocketDetailUseCase by lazy {
        GetRocketDetailUseCase(rocketRepository)
    }

    val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase by lazy {
        GetUpcomingLaunchesUseCase(rocketRepository)
    }
}