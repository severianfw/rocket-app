package com.severianfw.rocketapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severianfw.rocketapp.domain.model.Rocket
import com.severianfw.rocketapp.domain.usecase.GetRocketDetailUseCase
import com.severianfw.rocketapp.domain.usecase.GetRocketsUseCase
import com.severianfw.rocketapp.domain.usecase.GetUpcomingLaunchesUseCase
import com.severianfw.rocketapp.ui.state.RocketDetailUiState
import com.severianfw.rocketapp.ui.state.RocketLaunchUiState
import com.severianfw.rocketapp.ui.state.RocketUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RocketViewModel(
    private val getRocketsUseCase: GetRocketsUseCase,
    private val getRocketDetailUseCase: GetRocketDetailUseCase,
    private val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase
) : ViewModel() {

    private val _rawState = MutableStateFlow<RocketUiState>(RocketUiState.Loading)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _state = MutableStateFlow<RocketUiState>(RocketUiState.Loading)
    val state: StateFlow<RocketUiState> = _state.asStateFlow()

    private val _detailState = MutableStateFlow<RocketDetailUiState>(RocketDetailUiState.Loading)
    val detailState: StateFlow<RocketDetailUiState> = _detailState.asStateFlow()

    private val _launchState = MutableStateFlow<RocketLaunchUiState>(RocketLaunchUiState.Loading)
    val launchState: StateFlow<RocketLaunchUiState> = _launchState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(_rawState, _searchQuery) { state, query ->
                if (state is RocketUiState.Success && query.isNotBlank()) {
                    state.copy(rockets = state.rockets.filter {
                        it.name.contains(query, ignoreCase = true)
                    })
                } else {
                    state
                }
            }.collect { filteredState ->
                _state.value = filteredState
            }
        }

        getRockets()
        getUpcomingLaunches()
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun getRocketById(id: Int): Rocket? {
        val currentState = _rawState.value
        return if (currentState is RocketUiState.Success) {
            currentState.rockets.find { it.id == id }
        } else {
            null
        }
    }

    fun getRocketDetail(id: Int) {
        viewModelScope.launch {
            getRocketDetailUseCase(id)
                .onStart {
                    _detailState.value = RocketDetailUiState.Loading
                }
                .catch { e ->
                    val errorMessage = when (e) {
                        is IOException -> "Network error: Please check your internet connection"
                        is HttpException -> {
                            when (e.code()) {
                                404 -> "Resource not found"
                                else -> "Network error: ${e.code()}"
                            }
                        }
                        else -> e.message ?: "An unexpected error occurred"
                    }
                    _detailState.value = RocketDetailUiState.Error(errorMessage)
                }
                .collect { rocket ->
                    _detailState.value = RocketDetailUiState.Success(rocket)
                }
        }
    }

    fun getRockets() {
        viewModelScope.launch {
            getRocketsUseCase()
                .onStart {
                    _rawState.value = RocketUiState.Loading
                }
                .catch { e ->
                    val errorMessage = when (e) {
                        is IOException -> "Network error: Please check your internet connection"
                        is HttpException -> {
                            when (e.code()) {
                                404 -> "Resource not found"
                                else -> "Network error: ${e.code()}"
                            }
                        }
                        else -> e.message ?: "An unexpected error occurred"
                    }
                    _rawState.value = RocketUiState.Error(errorMessage)
                }
                .collect { rockets ->
                    _rawState.value = RocketUiState.Success(rockets)
                }
        }
    }

    fun getUpcomingLaunches() {
        viewModelScope.launch {
            getUpcomingLaunchesUseCase()
                .onStart {
                    _launchState.value = RocketLaunchUiState.Loading
                }
                .catch { e ->
                    val errorMessage = when (e) {
                        is IOException -> "Network error: Please check your internet connection"
                        is HttpException -> {
                            when (e.code()) {
                                404 -> "Resource not found"
                                else -> "Network error: ${e.code()}"
                            }
                        }
                        else -> e.message ?: "An unexpected error occurred"
                    }
                    _launchState.value = RocketLaunchUiState.Error(errorMessage)
                }
                .collect { launches ->
                    _launchState.value = RocketLaunchUiState.Success(launches)
                }
        }
    }
}
