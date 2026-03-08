package com.severianfw.rocketapp.data.repository

import com.severianfw.rocketapp.data.remote.RocketApiService
import com.severianfw.rocketapp.domain.model.Rocket
import com.severianfw.rocketapp.domain.repository.RocketRepository
import com.severianfw.rocketapp.util.toRocketModel
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
