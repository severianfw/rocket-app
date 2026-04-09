package com.severianfw.rocketapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Rocket(
    val id: Int,
    val name: String,
    val fullName: String,
    val family: String,
    val variant: String,
    val image: String,
    val description: String = "",
    val launchCost: String = "",
    val successLaunches: Int = 0,
    val successLandings: Int = 0,
    val failedLandings: Int = 0
)
