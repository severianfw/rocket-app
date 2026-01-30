package com.astro.rocketapp.severian.ui.state

import com.astro.rocketapp.severian.domain.model.Rocket

sealed class RocketUiState {
    data object Loading : RocketUiState()
    data class Success(val rockets: List<Rocket>) : RocketUiState()
    data class Error(val message: String) : RocketUiState()
}