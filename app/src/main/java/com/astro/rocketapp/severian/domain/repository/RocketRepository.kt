package com.astro.rocketapp.severian.domain.repository

import com.astro.rocketapp.severian.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

interface RocketRepository {
    fun getRockets(): Flow<List<Rocket>>
}
