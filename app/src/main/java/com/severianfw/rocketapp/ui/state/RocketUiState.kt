package com.severianfw.rocketapp.ui.state

import com.severianfw.rocketapp.domain.model.Rocket
import com.severianfw.rocketapp.domain.model.RocketLaunch

sealed class RocketUiState {
    data object Loading : RocketUiState()
    data class Success(val rockets: List<Rocket>) : RocketUiState()
    data class Error(val message: String) : RocketUiState()
}

sealed class RocketDetailUiState {
    data object Loading : RocketDetailUiState()
    data class Success(val rocket: Rocket) : RocketDetailUiState()
    data class Error(val message: String) : RocketDetailUiState()
}

sealed class RocketLaunchUiState {
    data object Loading : RocketLaunchUiState()
    data class Success(val launches: List<RocketLaunch>) : RocketLaunchUiState()
    data class Error(val message: String) : RocketLaunchUiState()
}
