package com.barisproduction.kargo.data.remote.model

import com.barisproduction.kargo.domain.model.AppUpdateConfig
import kotlinx.serialization.Serializable

@Serializable
data class AppUpdateConfigDto(
    val requireUpdate: Boolean = false,
    val androidMinBuildCode: Int = 0,
    val iosMinBuildCode: Int = 0,
    val androidStoreUrl: String = "",
    val iosStoreUrl: String = ""
)

fun AppUpdateConfigDto.toDomain(): AppUpdateConfig {
    return AppUpdateConfig(
        requireUpdate = requireUpdate,
        androidMinBuildCode = androidMinBuildCode,
        iosMinBuildCode = iosMinBuildCode,
        androidStoreUrl = androidStoreUrl,
        iosStoreUrl = iosStoreUrl
    )
}
