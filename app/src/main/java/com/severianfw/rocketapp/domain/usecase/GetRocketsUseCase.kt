package com.severianfw.rocketapp.domain.usecase

import com.severianfw.rocketapp.domain.model.Rocket
import com.severianfw.rocketapp.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow

class GetRocketsUseCase(private val repository: RocketRepository) {
    operator fun invoke(): Flow<List<Rocket>> = repository.getRockets()
}
