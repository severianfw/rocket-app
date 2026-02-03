package com.astro.rocketapp.severian.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.astro.rocketapp.severian.domain.usecase.GetRocketsUseCase

class RocketViewModelFactory(
    private val getRocketsUseCase: GetRocketsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RocketViewModel::class.java)) {
            return RocketViewModel(getRocketsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}