package com.barisproduction.kargo.ui.saveDialog

import com.barisproduction.kargo.domain.model.Parcels

object CargoDialogContract {

    data class UiState(
        val parcelList: List<Parcels> = emptyList(),
        val isLoading: Boolean = false,
        val trackingNumber: String = "",
        val cargoName: String = "",
        val detectedCarrier: Parcels? = null,
        val isCarrierSelectionVisible: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String? = null,
        val isTrackingNumberError: Boolean = false,
        val isParcelError: Boolean = false,
        val isCargoNameError: Boolean = false,
        val isEditMode: Boolean = false
    )

    sealed interface UiAction {
        data class OnTrackingNumberChange(val number: String) : UiAction
        data class OnCargoNameChange(val name: String) : UiAction
        data class OnCarrierSelected(val carrier: Parcels) : UiAction
        data object OnCarrierSelectClick : UiAction
        data object OnCarrierSelectDismiss : UiAction
        data object OnSaveClick : UiAction
    }

    sealed interface UiEffect {
        data object Dismiss : UiEffect
        data class ShowMessage(val message: String) : UiEffect
    }
}
