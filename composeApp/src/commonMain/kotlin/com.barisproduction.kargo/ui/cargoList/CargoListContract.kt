package com.barisproduction.kargo.ui.cargoList

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.model.ParcelModel

object CargoListContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<CargoModel> = emptyList(),
    )

    sealed class UiAction{
        data object AddNewCargo : UiAction()
        data class NavigateToTracking(val parcelModel: ParcelModel, val trackingNumber: String) : UiAction()
        data class DeleteCargo(val trackNo: String) : UiAction()
    }
    sealed class UiEffect{
        data class ShowError(val message: String) : UiEffect()
        data object NavigateToAddNewCargo : UiEffect()
        data class NavigateToTracking(val parcelModel: ParcelModel, val trackingNumber: String) : UiEffect()
    }
}