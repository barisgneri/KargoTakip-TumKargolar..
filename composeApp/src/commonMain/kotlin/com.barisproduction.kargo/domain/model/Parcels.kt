package com.barisproduction.kargo.domain.model

data class Parcels(
    val parcelName : String,
    val trackingUrl: String,
    val logo: String,
    val js: String?,
)