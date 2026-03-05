package com.barisproduction.kargo.navigation

import com.barisproduction.kargo.domain.model.Parcels
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
    @Serializable
    data class CargoSaveDialog(val parcelName: String, val trackingNo: String? ) : Screen


}