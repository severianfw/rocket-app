package com.severianfw.rocketapp.domain.usecase

import com.severianfw.rocketapp.domain.model.RocketLaunch
import com.severianfw.rocketapp.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow

class GetUpcomingLaunchesUseCase(private val repository: RocketRepository) {
    operator fun invoke(): Flow<List<RocketLaunch>> = repository.getUpcomingLaunches()
}
