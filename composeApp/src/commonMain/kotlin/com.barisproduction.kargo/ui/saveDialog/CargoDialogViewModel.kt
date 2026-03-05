package com.barisproduction.kargo.ui.saveDialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.ui.saveDialog.CargoDialogContract.UiState
import com.barisproduction.kargo.ui.saveDialog.CargoDialogContract.UiAction
import com.barisproduction.kargo.ui.saveDialog.CargoDialogContract.UiEffect
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.repository.CargoRepository
import com.barisproduction.kargo.domain.usecase.CheckCargoInDBUseCase
import com.barisproduction.kargo.domain.usecase.FindCargoInfoUseCase
import com.barisproduction.kargo.domain.usecase.InsertCargoUseCase
import com.barisproduction.kargo.navigation.Screen
import kotlinx.coroutines.launch

class CargoDialogViewModel(
    savedStateHandle: SavedStateHandle,
    private val findCargoInfoUseCase: FindCargoInfoUseCase,
    private val cargoRepository: CargoRepository,
    private val insertCargoUseCase: InsertCargoUseCase,
    private val checkCargoInDBUseCase: CheckCargoInDBUseCase
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {
    private val args = savedStateHandle.toRoute<Screen.CargoSaveDialog>()
    private val argsTrackingNumber = args.trackingNo
    private val argsParcelName = args.parcelName

    init {
        getParcelList()
        updateUiState { copy(trackingNumber = argsTrackingNumber ?: "") }
        findInfoInName()
    }

    private fun getParcelList() {
        viewModelScope.launch {
            cargoRepository.getCargoParcelListState().collect {
                when (it) {
                    is Resource.Success -> updateUiState {
                        copy(
                            parcelList = it.data ?: emptyList()
                        )
                    }

                    else -> updateUiState { copy(isLoading = true) }
                }
            }
        }
    }

    private fun findInfoInName() {
        when (val parcel = findCargoInfoUseCase(argsParcelName)) {
            is Resource.Success -> {
                updateUiState { copy(detectedCarrier = parcel.data) }
            }

            is Resource.Error -> {
                updateUiState { copy(errorMessage = parcel.message) }
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

    private fun handleSave() {
        viewModelScope.launch {
            val currentState = uiState.value
            if (currentState.trackingNumber.isNotBlank() &&
                currentState.detectedCarrier != null &&
                currentState.cargoName.isNotBlank() &&
                !checkCargoInDBUseCase(currentState.trackingNumber)
            ) {
                val entity = CargoModel(
                    cargoName = currentState.cargoName,
                    parcelName = currentState.detectedCarrier.parcelName,
                    logo = currentState.detectedCarrier.logo,
                    trackNo = currentState.trackingNumber,
                    addDate = null
                )
                insertCargoUseCase(entity)
                emitUiEffect(UiEffect.Dismiss)
            } else {
                updateUiState {
                    copy(
                        isTrackingNumberError = currentState.trackingNumber.isBlank(),
                        isParcelError = currentState.detectedCarrier == null,
                        isCargoNameError = currentState.cargoName.isBlank()
                    )
                }
                if (checkCargoInDBUseCase(currentState.trackingNumber)) {
                    emitUiEffect(UiEffect.ShowMessage("Bu numaraya ait bir gönderi zaten kayıtlı."))
                }
            }
        }
    }
}
