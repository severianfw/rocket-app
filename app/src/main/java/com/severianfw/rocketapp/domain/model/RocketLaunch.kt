package com.severianfw.rocketapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class RocketLaunch(
    val id: String,
    val net: String,
    val image: String,
    val rocketName: String,
    val missionName: String,
    val missionType: String,
    val orbitName: String,
    val statusName: String
)
