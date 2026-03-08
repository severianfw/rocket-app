package com.severianfw.rocketapp.util

import com.severianfw.rocketapp.data.remote.RocketResponseItem
import com.severianfw.rocketapp.domain.model.Rocket
import java.text.NumberFormat

fun RocketResponseItem.toRocketModel(): Rocket {
    return Rocket(
        id = id ?: "",
        name = name ?: "Unknown",
        description = description ?: "No description available",
        costPerLaunch = costPerLaunch ?: 0,
        country = country ?: "",
        firstFlight = firstFlight ?: "",
        image = flickrImages?.firstOrNull() ?: ""
    )
}

fun Int.formatToUSD(): String {
    val formatter = NumberFormat.getCurrencyInstance(java.util.Locale.US)
    formatter.maximumFractionDigits = 0
    return formatter.format(this)
}
