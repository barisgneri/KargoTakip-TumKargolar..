package com.barisproduction.kargo.navigation

import com.barisproduction.kargo.domain.model.ParcelModel
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Splash : Screen
    @Serializable
    data object CargoList : Screen
    @Serializable
    data object AddNewCargo : Screen
    @Serializable
    data class Tracking(val cargoParcel: ParcelModel, val trackingNumber: String) : Screen
}