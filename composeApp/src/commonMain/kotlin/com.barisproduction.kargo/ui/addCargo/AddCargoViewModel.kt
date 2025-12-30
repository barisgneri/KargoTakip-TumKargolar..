package com.barisproduction.kargo.ui.addCargo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi

import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiAction
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiEffect
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiState
import kotlinx.coroutines.launch

class AddCargoViewModel : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is UiAction.OnTrackingNumberChange -> {
                    updateUiState { copy(trackingNumber = uiAction.number) }
                    // TODO: Trigger carrier detection logic
                }
                is UiAction.OnCargoNameChange -> {
                    updateUiState { copy(cargoName = uiAction.name) }
                }
                is UiAction.OnBackClick -> {
                    emitUiEffect(UiEffect.NavigateBack)
                }
                UiAction.OnPasteClipboard -> {
                    // TODO: Implement clipboard handling
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
                    updateUiState { copy(detectedCarrier = uiAction.carrier, isCarrierSelectionVisible = false) }
                }
            }
        }
    }

    // Update state example: updateUiState { UiState(isLoading = false) }
    // or // updateUiState { copy(isLoading = false) }

    // Update effect example: emitUiEffect(UiEffect.ShowError(it.message.orEmpty()))
    // Use within a coroutine scope, e.g., viewModelScope.launch { ... }
}