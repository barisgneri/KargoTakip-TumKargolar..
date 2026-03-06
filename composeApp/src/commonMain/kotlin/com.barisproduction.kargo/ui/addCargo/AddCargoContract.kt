package com.barisproduction.kargo.ui.addCargo

import com.barisproduction.kargo.domain.model.Parcels

object AddCargoContract {
    data class UiState(
        val isLoading: Boolean = false,
        val parcelList: List<Parcels> = emptyList(),
        val trackingNumber: String = "",
        val cargoName: String? = null,
        val trackingNumberError: Boolean = false,
        val cargoNameError: Boolean = false,
        val detectedCarrier: Parcels? = null,
        val isCarrierLoading: Boolean = false,
        val isCarrierSelectionVisible: Boolean = false
    )

    sealed interface UiAction {
        data class OnTrackingNumberChange(val number: String) : UiAction
        data class OnCargoNameChange(val name: String) : UiAction
        data class OnCarrierSelected(val carrier: Parcels) : UiAction
        data object OnPasteClipboard : UiAction
        data object OnScanBarcode : UiAction
        data object OnSaveClick : UiAction
        data object OnSearchCargoClick : UiAction
        data object OnBackClick : UiAction
        data object OnCarrierSelectClick : UiAction
        data object OnCarrierSelectDismiss : UiAction
    }

    sealed interface UiEffect {
        data object NavigateBack : UiEffect
        data class NavigateToSearch(val cargoName: String, val trackingNo: String) : UiEffect
        data class ShowSaveDialog(val parcelName: String, val trackingNo: String) : UiEffect
        data class ShowToast(val message: String) : UiEffect
    }
}