package com.severianfw.rocketapp.domain.repository

import com.severianfw.rocketapp.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

interface RocketRepository {
    fun getRockets(): Flow<List<Rocket>>
}
