package com.barisproduction.kargo.ui.tracking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.navigation.Screen
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiEffect
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiState
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiAction
import kotlinx.coroutines.launch

class TrackingViewModel (
    savedStateHandle: SavedStateHandle
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {
    private val args = savedStateHandle.toRoute<Screen.Tracking>()
    private val argsTrackingUrl = args.trackingNumber

    init {
        updateUiState { copy(trackingUrl = argsTrackingUrl) }
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is UiAction.OnBackClick -> emitUiEffect(UiEffect.NavigateBack)
                is UiAction.OnSaveClick -> {}
                is UiAction.OnLoadingStateChanged -> updateUiState { copy(isLoading = uiAction.isLoading) }
                is UiAction.OnErrorReceived -> updateUiState { copy(isError = true, errorMessage = uiAction.message) }
                is UiAction.OnRetryClick -> updateUiState { copy(isError = false, isLoading = true) }
            }
        }
    }
}
