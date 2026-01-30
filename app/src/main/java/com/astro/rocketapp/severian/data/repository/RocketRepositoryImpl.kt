package com.astro.rocketapp.severian.data.repository

import com.astro.rocketapp.severian.data.remote.RocketApiService
import com.astro.rocketapp.severian.domain.model.Rocket
import com.astro.rocketapp.severian.domain.repository.RocketRepository
import com.astro.rocketapp.severian.util.toRocketModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RocketRepositoryImpl(
    private val apiService: RocketApiService
) : RocketRepository {

    override fun getRockets(): Flow<List<Rocket>> = flow {
        val response = apiService.getRockets()
        emit(response.map { it.toRocketModel() })
    }
}
