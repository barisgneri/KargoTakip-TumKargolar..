package com.barisproduction.kargo.ui.addCargo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.common.service.ClipboardService
import com.barisproduction.kargo.data.local.CargoEntity
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.model.ParcelModel
import com.barisproduction.kargo.domain.usecase.InsertCargoUseCase
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiAction
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiEffect
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiState
import kotlinx.coroutines.launch

class AddCargoViewModel(
    private val clipboardService: ClipboardService,
    private val insertCargoUseCase: InsertCargoUseCase
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is UiAction.OnTrackingNumberChange -> updateTrackingNumber(uiAction.number)
                is UiAction.OnCargoNameChange -> updateCargoName(uiAction.name)
                is UiAction.OnBackClick -> emitUiEffect(UiEffect.NavigateBack)
                is UiAction.OnPasteClipboard -> handlePaste()
                is UiAction.OnSearchCargoClick -> handleSearch()
                is UiAction.OnSaveClick -> handleSave()
                is UiAction.OnCarrierSelectClick -> updateUiState { copy(isCarrierSelectionVisible = true) }
                is UiAction.OnCarrierSelectDismiss -> updateUiState { copy(isCarrierSelectionVisible = false) }
                is UiAction.OnCarrierSelected -> selectCarrier(uiAction)
                UiAction.OnScanBarcode -> { /* TODO */ }
            }
        }
    }

    private fun updateTrackingNumber(number: String) {
        updateUiState { copy(trackingNumber = number, trackingNumberError = false) }
    }

    private fun updateCargoName(name: String) {
        updateUiState { copy(cargoName = name, cargoNameError = false) }
    }

    private fun selectCarrier(action: UiAction.OnCarrierSelected) {
        updateUiState {
            copy(
                detectedCarrier = action.carrier,
                isCarrierSelectionVisible = false,
                cargoNameError = false
            )
        }
    }

    private fun handlePaste() {
        viewModelScope.launch {
            clipboardService.getText()?.let { text ->
                if (text.isNotBlank()) {
                    updateUiState { copy(trackingNumber = text) }
                }
            }
        }
    }

    private fun handleSearch() {
        if (validateInputs()) {
            viewModelScope.launch {
                emitUiEffect(UiEffect.NavigateToSearch(uiState.value.detectedCarrier, uiState.value.trackingNumber))
            }
        }
    }

    private fun handleSave() {
        if (validateInputs()) {
            viewModelScope.launch {
                val entity = CargoModel(
                    parcel = uiState.value.detectedCarrier ?: ParcelModel.OTHER,
                    trackNo = uiState.value.trackingNumber,
                    addDate = null
                )
                insertCargoUseCase(entity)
                emitUiEffect(UiEffect.ShowToast("Kargo başarıyla kaydedildi"))
                emitUiEffect(UiEffect.NavigateBack)
            }
        }
    }

    private fun validateInputs(): Boolean {
        val trackingError = uiState.value.trackingNumber.isBlank()
        val carrierError = uiState.value.detectedCarrier == null

        if (trackingError || carrierError) {
            updateUiState {
                copy(
                    trackingNumberError = trackingError,
                    cargoNameError = carrierError
                )
            }
            return false
        }
        return true
    }
}
