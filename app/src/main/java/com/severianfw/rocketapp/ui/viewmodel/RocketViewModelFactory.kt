package com.severianfw.rocketapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.severianfw.rocketapp.domain.usecase.GetRocketDetailUseCase
import com.severianfw.rocketapp.domain.usecase.GetRocketsUseCase
import com.severianfw.rocketapp.domain.usecase.GetUpcomingLaunchesUseCase

class RocketViewModelFactory(
    private val getRocketsUseCase: GetRocketsUseCase,
    private val getRocketDetailUseCase: GetRocketDetailUseCase,
    private val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RocketViewModel::class.java)) {
            return RocketViewModel(getRocketsUseCase, getRocketDetailUseCase, getUpcomingLaunchesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
