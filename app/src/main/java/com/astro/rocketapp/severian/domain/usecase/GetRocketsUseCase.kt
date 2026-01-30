package com.astro.rocketapp.severian.domain.usecase

import com.astro.rocketapp.severian.domain.model.Rocket
import com.astro.rocketapp.severian.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow

class GetRocketsUseCase(private val repository: RocketRepository) {
    operator fun invoke(): Flow<List<Rocket>> = repository.getRockets()
}
