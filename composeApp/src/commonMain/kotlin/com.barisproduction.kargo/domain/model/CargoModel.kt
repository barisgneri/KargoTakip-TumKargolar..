package com.barisproduction.kargo.domain.model

data class CargoModel(
    val parcel: ParcelModel,
    val trackNo: String,
    val addDate: String?
)
