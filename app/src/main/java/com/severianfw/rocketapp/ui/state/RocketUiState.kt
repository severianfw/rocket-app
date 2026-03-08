package com.severianfw.rocketapp.ui.state

import com.severianfw.rocketapp.domain.model.Rocket

sealed class RocketUiState {
    data object Loading : RocketUiState()
    data class Success(val rockets: List<Rocket>) : RocketUiState()
    data class Error(val message: String) : RocketUiState()
}
