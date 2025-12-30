package com.barisproduction.kargo.domain.model

data class CargoModel(
    val cargoName: String,
    val parcel: ParcelModel,
    val trackNo: String,
    val addDate: Long
)
