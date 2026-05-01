package com.barisproduction.kargo.domain.model

data class AppUpdateConfig(
    val requireUpdate: Boolean,
    val androidMinBuildCode: Int,
    val iosMinBuildCode: Int,
    val androidStoreUrl: String,
    val iosStoreUrl: String
)
