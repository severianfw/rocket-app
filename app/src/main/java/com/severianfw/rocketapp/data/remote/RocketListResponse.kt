package com.severianfw.rocketapp.data.remote

import com.google.gson.annotations.SerializedName

data class RocketListResponse(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<RocketListResultItem>? = null
)

data class ManufacturerResponse(

	@field:SerializedName("parent")
	val parent: Any? = null,

	@field:SerializedName("featured")
	val featured: Boolean? = null,

	@field:SerializedName("wiki_url")
	val wikiUrl: String? = null,

	@field:SerializedName("logo_url")
	val logoUrl: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("pending_launches")
	val pendingLaunches: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("successful_launches")
	val successfulLaunches: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("launchers")
	val launchers: String? = null,

	@field:SerializedName("spacecraft")
	val spacecraft: String? = null,

	@field:SerializedName("administrator")
	val administrator: String? = null,

	@field:SerializedName("founding_year")
	val foundingYear: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("failed_landings")
	val failedLandings: Int? = null,

	@field:SerializedName("launch_library_url")
	val launchLibraryUrl: Any? = null,

	@field:SerializedName("consecutive_successful_landings")
	val consecutiveSuccessfulLandings: Int? = null,

	@field:SerializedName("consecutive_successful_launches")
	val consecutiveSuccessfulLaunches: Int? = null,

	@field:SerializedName("successful_landings")
	val successfulLandings: Int? = null,

	@field:SerializedName("nation_url")
	val nationUrl: String? = null,

	@field:SerializedName("attempted_landings")
	val attemptedLandings: Int? = null,

	@field:SerializedName("failed_launches")
	val failedLaunches: Int? = null,

	@field:SerializedName("abbrev")
	val abbrev: String? = null,

	@field:SerializedName("total_launch_count")
	val totalLaunchCount: Int? = null,

	@field:SerializedName("info_url")
	val infoUrl: String? = null
)

data class RocketListResultItem(

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("wiki_url")
	val wikiUrl: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("variant")
	val variant: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("family")
	val family: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("info_url")
	val infoUrl: Any? = null,

	@field:SerializedName("manufacturer")
	val manufacturer: ManufacturerResponse? = null,

	@field:SerializedName("reusable")
	val reusable: Boolean? = null
)
