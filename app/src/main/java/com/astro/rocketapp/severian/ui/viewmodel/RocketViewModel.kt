package com.astro.rocketapp.severian.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astro.rocketapp.severian.domain.model.Rocket
import com.astro.rocketapp.severian.domain.usecase.GetRocketsUseCase
import com.astro.rocketapp.severian.ui.state.RocketUiState
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
    private val getRocketsUseCase: GetRocketsUseCase
) : ViewModel() {

    private val _rawState = MutableStateFlow<RocketUiState>(RocketUiState.Loading)
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _state = MutableStateFlow<RocketUiState>(RocketUiState.Loading)
    val state: StateFlow<RocketUiState> = _state.asStateFlow()

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
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun getRocketById(id: String): Rocket? {
        val currentState = _rawState.value
        return if (currentState is RocketUiState.Success) {
            currentState.rockets.find { it.id == id }
        } else {
            null
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
}
