package com.barisproduction.kargo.data.remote.model

import com.barisproduction.kargo.domain.model.Parcels
import kotlinx.serialization.Serializable

@Serializable
data class CargoDto (
    val name: String,
    val url: String,
    val logo: String,
    val js: String
)

fun CargoDto.toDomain(): Parcels{
    return Parcels(
        parcelName = name,
        trackingUrl = url,
        logo = logo,
        js = js
    )
}