package com.barisproduction.kargo.ui.saveDialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.common.extensions.toUserMessage
import com.barisproduction.kargo.ui.saveDialog.CargoDialogContract.UiState
import com.barisproduction.kargo.ui.saveDialog.CargoDialogContract.UiAction
import com.barisproduction.kargo.ui.saveDialog.CargoDialogContract.UiEffect
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.usecase.CheckCargoInDBUseCase
import com.barisproduction.kargo.domain.usecase.FindCargoInfoUseCase
import com.barisproduction.kargo.domain.usecase.GetCargoParcelListUseCase
import com.barisproduction.kargo.domain.usecase.InsertCargoUseCase
import com.barisproduction.kargo.navigation.Screen
import kotlinx.coroutines.launch

class CargoDialogViewModel(
    savedStateHandle: SavedStateHandle,
    private val findCargoInfoUseCase: FindCargoInfoUseCase,
    private val getCargoParcelListUseCase: GetCargoParcelListUseCase,
    private val insertCargoUseCase: InsertCargoUseCase,
    private val checkCargoInDBUseCase: CheckCargoInDBUseCase
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    init {
        val args = savedStateHandle.toRoute<Screen.CargoSaveDialog>()

        getParcelList()
        updateUiState { copy(trackingNumber = args.trackingNo ?: "") }
        findInfoInName(args.parcelName)
    }

    private fun getParcelList() = viewModelScope.launch {
        getCargoParcelListUseCase().collect { result ->
            when (result) {
                is Resource.Loading -> updateUiState { copy(isLoading = true) }
                is Resource.Success -> updateUiState {
                    copy(
                        parcelList = result.data ?: emptyList(),
                        isLoading = false
                    )
                }

                is Resource.Error -> updateUiState {
                    copy(
                        isLoading = false,
                        errorMessage = result.errorType.toUserMessage()
                    )
                }
            }
        }
    }

    private fun findInfoInName(parcelName: String) {
        when (val parcel = findCargoInfoUseCase(parcelName)) {
            is Resource.Success -> {
                updateUiState { copy(detectedCarrier = parcel.data) }
            }

            is Resource.Error -> {
                updateUiState { copy(errorMessage = parcel.errorType.toUserMessage()) }
            }

            is Resource.Loading -> {
                updateUiState { copy(isLoading = true) }
            }
        }
    }

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnTrackingNumberChange -> updateUiState { copy(trackingNumber = uiAction.number) }
            is UiAction.OnCarrierSelected -> updateUiState {
                copy(detectedCarrier = uiAction.carrier, isCarrierSelectionVisible = false)
            }

            is UiAction.OnCarrierSelectClick -> updateUiState { copy(isCarrierSelectionVisible = true) }
            is UiAction.OnCarrierSelectDismiss -> updateUiState { copy(isCarrierSelectionVisible = false) }
            is UiAction.OnCargoNameChange -> updateUiState { copy(cargoName = uiAction.name) }
            is UiAction.OnSaveClick -> handleSave()
        }
    }

    private fun handleSave(isEditMode: Boolean = false) {
        viewModelScope.launch {
            val currentState = uiState.value

            val trackingNo = currentState.trackingNumber.trim()
            val cargoName = currentState.cargoName.trim()
            val carrier = currentState.detectedCarrier

            updateUiState {
                copy(
                    isTrackingNumberError = trackingNo.isBlank(),
                    isParcelError = carrier == null,
                    isCargoNameError = cargoName.isBlank()
                )
            }

            if (trackingNo.isBlank() || cargoName.isBlank() || carrier == null) return@launch

            if (!isEditMode) {
                if (checkCargoInDBUseCase(trackingNo)) {
                    emitUiEffect(UiEffect.ShowMessage("Bu numaraya ait bir gönderi zaten kayıtlı."))
                    return@launch
                }
            }

            val entity = CargoModel(
                cargoName = cargoName,
                parcelName = carrier.parcelName,
                logo = carrier.logo,
                trackNo = trackingNo,
                addDate = null
            )

            if (isEditMode) {
                // updateCargoUseCase(entity)
            } else {
                insertCargoUseCase(entity)
            }

            emitUiEffect(UiEffect.Dismiss)
        }
    }
}
