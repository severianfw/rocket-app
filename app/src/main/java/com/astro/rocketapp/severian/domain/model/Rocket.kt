package com.astro.rocketapp.severian.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Rocket(
    val id: String,
    val name: String,
    val description: String,
    val costPerLaunch: Int,
    val country: String,
    val firstFlight: String,
    val image: String
)
