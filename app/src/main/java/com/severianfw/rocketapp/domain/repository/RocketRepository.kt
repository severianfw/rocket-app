package com.severianfw.rocketapp.domain.repository

import com.severianfw.rocketapp.domain.model.Rocket
import com.severianfw.rocketapp.domain.model.RocketLaunch
import kotlinx.coroutines.flow.Flow

interface RocketRepository {
    fun getRockets(): Flow<List<Rocket>>

    fun getRocketDetail(id: Int): Flow<Rocket>

    fun getUpcomingLaunches(): Flow<List<RocketLaunch>>
}
