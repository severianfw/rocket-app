package com.severianfw.rocketapp.data.remote

import com.google.gson.annotations.SerializedName

data class RocketResponseItem(

    @field:SerializedName("second_stage")
    val secondStage: SecondStage? = null,

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("mass")
    val mass: Mass? = null,

    @field:SerializedName("active")
    val active: Boolean? = null,

    @field:SerializedName("cost_per_launch")
    val costPerLaunch: Int? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("payload_weights")
    val payloadWeights: List<PayloadWeightsItem?>? = null,

    @field:SerializedName("first_flight")
    val firstFlight: String? = null,

    @field:SerializedName("landing_legs")
    val landingLegs: LandingLegs? = null,

    @field:SerializedName("diameter")
    val diameter: Diameter? = null,

    @field:SerializedName("flickr_images")
    val flickrImages: List<String?>? = null,

    @field:SerializedName("first_stage")
    val firstStage: FirstStage? = null,

    @field:SerializedName("engines")
    val engines: Engines? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("stages")
    val stages: Int? = null,

    @field:SerializedName("boosters")
    val boosters: Int? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("success_rate_pct")
    val successRatePct: Int? = null,

    @field:SerializedName("wikipedia")
    val wikipedia: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("height")
    val height: Height? = null
)

data class Isp(

	@field:SerializedName("vacuum")
	val vacuum: Int? = null,

	@field:SerializedName("sea_level")
	val seaLevel: Int? = null
)

data class LandingLegs(

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("material")
	val material: Any? = null
)

data class Mass(

	@field:SerializedName("lb")
	val lb: Int? = null,

	@field:SerializedName("kg")
	val kg: Int? = null
)

data class ThrustVacuum(

	@field:SerializedName("lbf")
	val lbf: Int? = null,

	@field:SerializedName("kN")
	val kN: Int? = null
)

data class CompositeFairing(

	@field:SerializedName("diameter")
	val diameter: Diameter? = null,

	@field:SerializedName("height")
	val height: Height? = null
)

data class Diameter(

	@field:SerializedName("feet")
	val feet: Any? = null,

	@field:SerializedName("meters")
	val meters: Any? = null
)

data class Height(

	@field:SerializedName("feet")
	val feet: Any? = null,

	@field:SerializedName("meters")
	val meters: Any? = null
)

data class FirstStage(

	@field:SerializedName("thrust_sea_level")
	val thrustSeaLevel: ThrustSeaLevel? = null,

	@field:SerializedName("fuel_amount_tons")
	val fuelAmountTons: Any? = null,

	@field:SerializedName("thrust_vacuum")
	val thrustVacuum: ThrustVacuum? = null,

	@field:SerializedName("engines")
	val engines: Int? = null,

	@field:SerializedName("burn_time_sec")
	val burnTimeSec: Int? = null,

	@field:SerializedName("reusable")
	val reusable: Boolean? = null
)

data class Payloads(

	@field:SerializedName("composite_fairing")
	val compositeFairing: CompositeFairing? = null,

	@field:SerializedName("option_1")
	val option1: String? = null
)

data class ThrustSeaLevel(

	@field:SerializedName("lbf")
	val lbf: Int? = null,

	@field:SerializedName("kN")
	val kN: Int? = null
)

data class SecondStage(

	@field:SerializedName("fuel_amount_tons")
	val fuelAmountTons: Any? = null,

	@field:SerializedName("payloads")
	val payloads: Payloads? = null,

	@field:SerializedName("engines")
	val engines: Int? = null,

	@field:SerializedName("burn_time_sec")
	val burnTimeSec: Int? = null,

	@field:SerializedName("thrust")
	val thrust: Thrust? = null,

	@field:SerializedName("reusable")
	val reusable: Boolean? = null
)

data class Thrust(

	@field:SerializedName("lbf")
	val lbf: Int? = null,

	@field:SerializedName("kN")
	val kN: Int? = null
)

data class Engines(

	@field:SerializedName("layout")
	val layout: String? = null,

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("propellant_1")
	val propellant1: String? = null,

	@field:SerializedName("thrust_sea_level")
	val thrustSeaLevel: ThrustSeaLevel? = null,

	@field:SerializedName("engine_loss_max")
	val engineLossMax: Int? = null,

	@field:SerializedName("thrust_to_weight")
	val thrustToWeight: Double? = null,

	@field:SerializedName("thrust_vacuum")
	val thrustVacuum: ThrustVacuum? = null,

	@field:SerializedName("isp")
	val isp: Isp? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("version")
	val version: String? = null,

	@field:SerializedName("propellant_2")
	val propellant2: String? = null
)

data class PayloadWeightsItem(

	@field:SerializedName("lb")
	val lb: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("kg")
	val kg: Int? = null
)
