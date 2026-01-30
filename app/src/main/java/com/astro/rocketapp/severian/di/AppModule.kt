package com.astro.rocketapp.severian.di

import com.astro.rocketapp.severian.data.repository.RocketRepositoryImpl
import com.astro.rocketapp.severian.domain.repository.RocketRepository
import com.astro.rocketapp.severian.domain.usecase.GetRocketsUseCase

object AppModule {

    val rocketRepository: RocketRepository by lazy {
        RocketRepositoryImpl(NetworkModule.apiService)
    }

    val getRocketsUseCase: GetRocketsUseCase by lazy {
        GetRocketsUseCase(rocketRepository)
    }
}
