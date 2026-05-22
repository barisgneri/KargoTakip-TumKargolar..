package com.barisproduction.kargo.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryResponse(
    @SerialName("code") val code: String,
    @SerialName("name") val name: String,
    @SerialName("flag_emoji") val flagEmoji: String
)
