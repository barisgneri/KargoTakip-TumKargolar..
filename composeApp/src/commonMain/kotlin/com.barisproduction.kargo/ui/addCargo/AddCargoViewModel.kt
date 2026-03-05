package com.barisproduction.kargo.ui.addCargo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.common.service.ClipboardService
import com.barisproduction.kargo.data.local.CargoEntity
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.repository.CargoRepository
import com.barisproduction.kargo.domain.usecase.InsertCargoUseCase
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiAction
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiEffect
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiState
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddCargoViewModel(
    private val clipboardService: ClipboardService,
    private val insertCargoUseCase: InsertCargoUseCase,
    private val cargoRepository: CargoRepository
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    init {
        getParcelList()
    }

    private fun getParcelList() {
        viewModelScope.launch {
            cargoRepository.getCargoParcelListState().collect{
                when(it){
                    is Resource.Success -> updateUiState { copy(parcelList = it.data ?: emptyList()) }

                    else -> updateUiState { copy(isLoading = true) }
                }
            }
        }
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is UiAction.OnTrackingNumberChange -> updateTrackingNumber(uiAction.number)
                is UiAction.OnCargoNameChange -> updateCargoName(uiAction.name)
                is UiAction.OnBackClick -> emitUiEffect(UiEffect.NavigateBack)
                is UiAction.OnPasteClipboard -> handlePaste()
                is UiAction.OnSearchCargoClick -> handleSearch()
                is UiAction.OnSaveClick -> {
                    emitUiEffect(UiEffect.ShowSaveDialog(uiState.value.detectedCarrier?.parcelName ?: "", uiState.value.trackingNumber))
                }
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
                emitUiEffect(UiEffect.NavigateToSearch(uiState.value.detectedCarrier?.parcelName ?: "", uiState.value.trackingNumber))
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
