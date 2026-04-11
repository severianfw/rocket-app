package com.severianfw.rocketapp.data.repository

import com.severianfw.rocketapp.data.remote.RocketApiService
import com.severianfw.rocketapp.domain.model.Rocket
import com.severianfw.rocketapp.domain.model.RocketLaunch
import com.severianfw.rocketapp.domain.repository.RocketRepository
import com.severianfw.rocketapp.util.toRocketLaunchModel
import com.severianfw.rocketapp.util.toRocketModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RocketRepositoryImpl(
    private val apiService: RocketApiService
) : RocketRepository {

    override fun getRockets(): Flow<List<Rocket>> = flow {
        val results = apiService.getRockets().results ?: emptyList()
        emit(results.map { it.toRocketModel() })
    }

    override fun getRocketDetail(id: Int): Flow<Rocket> = flow {
        val response = apiService.getRocketDetail(id)
        emit(response.toRocketModel())
    }

    override fun getUpcomingLaunches(): Flow<List<RocketLaunch>> = flow {
        val results = apiService.getUpcomingLaunches().results ?: emptyList()
        emit(results.mapNotNull { it?.toRocketLaunchModel() })
    }
}
