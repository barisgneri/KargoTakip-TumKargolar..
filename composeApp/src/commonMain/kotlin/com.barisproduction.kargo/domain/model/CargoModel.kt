package com.barisproduction.kargo.domain.model


data class CargoModel(
    val parcelName: String,
    val cargoName: String?,
    val logo: String,
    val trackNo: String,
    val createdAt: Long? = null,
    val companyCountryCode: String? = null
)
