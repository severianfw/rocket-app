package com.severianfw.rocketapp.data.remote

import com.google.gson.annotations.SerializedName

data class RocketDetailResponse(

	@field:SerializedName("min_stage")
	val minStage: Int? = null,

	@field:SerializedName("apogee")
	val apogee: Any? = null,

	@field:SerializedName("launch_mass")
	val launchMass: Int? = null,

	@field:SerializedName("wiki_url")
	val wikiUrl: String? = null,

	@field:SerializedName("pending_launches")
	val pendingLaunches: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("program")
	val program: List<Any?>? = null,

	@field:SerializedName("successful_launches")
	val successfulLaunches: Int? = null,

	@field:SerializedName("maiden_flight")
	val maidenFlight: String? = null,

	@field:SerializedName("manufacturer")
	val manufacturer: ManufacturerResponse? = null,

	@field:SerializedName("diameter")
	val diameter: Any? = null,

	@field:SerializedName("variant")
	val variant: String? = null,

	@field:SerializedName("launch_cost")
	val launchCost: String? = null,

	@field:SerializedName("alias")
	val alias: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("leo_capacity")
	val leoCapacity: Int? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("failed_landings")
	val failedLandings: Int? = null,

	@field:SerializedName("length")
	val length: Any? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("to_thrust")
	val toThrust: Int? = null,

	@field:SerializedName("max_stage")
	val maxStage: Int? = null,

	@field:SerializedName("consecutive_successful_landings")
	val consecutiveSuccessfulLandings: Int? = null,

	@field:SerializedName("consecutive_successful_launches")
	val consecutiveSuccessfulLaunches: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("successful_landings")
	val successfulLandings: Int? = null,

	@field:SerializedName("reusable")
	val reusable: Boolean? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("attempted_landings")
	val attemptedLandings: Int? = null,

	@field:SerializedName("vehicle_range")
	val vehicleRange: Any? = null,

	@field:SerializedName("failed_launches")
	val failedLaunches: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("gto_capacity")
	val gtoCapacity: Any? = null,

	@field:SerializedName("family")
	val family: String? = null,

	@field:SerializedName("info_url")
	val infoUrl: Any? = null,

	@field:SerializedName("total_launch_count")
	val totalLaunchCount: Int? = null
)
