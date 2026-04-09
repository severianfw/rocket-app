package com.severianfw.rocketapp.util

import com.severianfw.rocketapp.data.remote.RocketDetailResponse
import com.severianfw.rocketapp.data.remote.RocketListResultItem
import com.severianfw.rocketapp.domain.model.Rocket
import java.text.NumberFormat

fun RocketListResultItem.toRocketModel(): Rocket {
    return Rocket(
        id = id ?: 0,
        name = name ?: "Unknown",
        fullName = fullName ?: "Unknown",
        family = family ?: "Unknown",
        variant = variant ?: "Unknown",
        image = imageUrl ?: ""
    )
}

fun RocketDetailResponse.toRocketModel(): Rocket {
    return Rocket(
        id = id ?: 0,
        name = name ?: "Unknown",
        fullName = fullName ?: "Unknown",
        family = family ?: "Unknown",
        variant = variant ?: "Unknown",
        image = imageUrl ?: "",
        description = description ?: "",
        launchCost = launchCost?.formatToUSD() ?: "",
        successLaunches = successfulLaunches ?: 0,
        successLandings = successfulLandings ?: 0,
        failedLandings = failedLandings ?: 0
    )
}

fun String.formatToUSD(): String {
    val amount = this.toLongOrNull() ?: return this
    val formatter = NumberFormat.getCurrencyInstance(java.util.Locale.US)
    formatter.maximumFractionDigits = 0
    return formatter.format(amount)
}
