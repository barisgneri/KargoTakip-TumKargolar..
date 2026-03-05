package com.barisproduction.kargo.ui.tracking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.common.createTrackUrl
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.usecase.CheckCargoInDBUseCase
import com.barisproduction.kargo.domain.usecase.FindCargoInfoUseCase
import com.barisproduction.kargo.domain.usecase.InsertCargoUseCase
import com.barisproduction.kargo.navigation.Screen
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiEffect
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiState
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiAction
import kotlinx.coroutines.launch

class TrackingViewModel (
    savedStateHandle: SavedStateHandle,
    private val checkCargoInDBUseCase: CheckCargoInDBUseCase,
    private val findCargoInfoUseCase: FindCargoInfoUseCase,
    ) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {
    private val args = savedStateHandle.toRoute<Screen.Tracking>()
    private val argsTrackingNumber = args.trackingNo
    private val argsParcelName = args.parcelName

    init {
        findInfoInName()
        viewModelScope.launch {
            val isVisible = !isHaveDB()
            updateUiState { copy(saveButtonVisibility = isVisible) }
        }
    }

    private fun findInfoInName() {
        when(val parcel = findCargoInfoUseCase(argsParcelName)){
            is Resource.Success -> {
                val url = createTrackUrl(trackingUrl = parcel.data?.trackingUrl ?: "", trackingNumber = argsTrackingNumber)
                updateUiState { copy(trackingUrl = url, logo = parcel.data?.logo, js = parcel.data?.js ?: "") }
            }
            is Resource.Error -> {
                updateUiState { copy(isError = true, errorMessage = parcel.message) }
            }
            is Resource.Loading -> {
                updateUiState { copy(isLoading = true) }
            }
        }
    }


    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is UiAction.OnBackClick -> emitUiEffect(UiEffect.NavigateBack)
                is UiAction.OnSaveClick -> emitUiEffect(UiEffect.ShowSaveDialog(argsParcelName, argsTrackingNumber))
                is UiAction.OnLoadingStateChanged -> updateUiState { copy(isLoading = uiAction.isLoading) }
                is UiAction.OnErrorReceived -> updateUiState { copy(isError = true, errorMessage = uiAction.message) }
                is UiAction.OnRetryClick -> updateUiState { copy(isError = false, isLoading = true) }
            }
        }
    }


    private suspend fun isHaveDB() : Boolean = checkCargoInDBUseCase(argsTrackingNumber)
}
