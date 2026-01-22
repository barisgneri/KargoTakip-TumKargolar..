package com.barisproduction.kargo.ui.addCargo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.barisproduction.kargo.common.createTrackUrl
import com.barisproduction.kargo.common.service.ClipboardService
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.domain.model.ParcelModel

import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiAction
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiEffect
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiState
import kotlinx.coroutines.launch

class AddCargoViewModel(private val clipboardService: ClipboardService) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is UiAction.OnTrackingNumberChange -> {
                    updateUiState { copy(trackingNumber = uiAction.number, trackingNumberError = false) }
                }
                is UiAction.OnCargoNameChange -> {
                    updateUiState { copy(cargoName = uiAction.name, cargoNameError = false) }
                }
                is UiAction.OnBackClick -> {
                    emitUiEffect(UiEffect.NavigateBack)
                }
                is UiAction.OnPasteClipboard -> {
                    onPasteClicked()
                }
                is UiAction.OnSearchCargoClick -> {
                    searchCargo()
                }
                UiAction.OnScanBarcode -> {
                    // TODO: Implement barcode scanning
                }
                UiAction.OnSaveClick -> {
                    // TODO: Implement save logic
                    emitUiEffect(UiEffect.NavigateBack) 
                }
                UiAction.OnCarrierSelectClick -> {
                    updateUiState { copy(isCarrierSelectionVisible = true) }
                }
                UiAction.OnCarrierSelectDismiss -> {
                    updateUiState { copy(isCarrierSelectionVisible = false) }
                }
                is UiAction.OnCarrierSelected -> {
                    updateUiState { copy(detectedCarrier = uiAction.carrier, isCarrierSelectionVisible = false, cargoNameError = false) }
                }
            }
        }
    }

    private fun searchCargo() {
        val currentTrackingNo = uiState.value.trackingNumber
        val detectedCarrier = uiState.value.detectedCarrier

        val trackingError = currentTrackingNo.isBlank()
        val cargoError = detectedCarrier?.name.isNullOrBlank()

        if (trackingError || cargoError) {
            updateUiState {
                copy(
                    trackingNumberError = trackingError,
                    cargoNameError = cargoError
                )
            }
            return
        }

        viewModelScope.launch {
            val getUrl = createTrackUrl(currentTrackingNo, detectedCarrier)
            emitUiEffect(UiEffect.NavigateToSearch(getUrl))
        }
    }

    private fun onPasteClicked() {
        viewModelScope.launch {
            val text = clipboardService.getText()
            if (!text.isNullOrBlank()) {
                updateUiState { copy(trackingNumber = text) }
                // Oto firma tanıma eklenecek
            }
        }
    }
}