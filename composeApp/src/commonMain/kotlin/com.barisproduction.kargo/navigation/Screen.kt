package com.barisproduction.kargo.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Splash : Screen
    @Serializable
    data object CargoList : Screen
    @Serializable
    data object AddNewCargo : Screen
    @Serializable
    data class Tracking(val parcelName: String, val trackingNo: String) : Screen
}