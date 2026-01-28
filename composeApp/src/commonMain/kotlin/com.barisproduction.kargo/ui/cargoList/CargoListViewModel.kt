package com.barisproduction.kargo.ui.cargoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.domain.usecase.GetCargosUseCase

import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiAction
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiEffect
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CargoListViewModel(private val getCargosUseCase: GetCargosUseCase) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is UiAction.AddNewCargo -> emitUiEffect(UiEffect.NavigateToAddNewCargo)
                is UiAction.NavigateToTracking -> emitUiEffect(UiEffect.NavigateToTracking(uiAction.parcelModel, uiAction.trackingNumber))
            }
        }
    }

    init {
        getCargosUseCase().onEach {
            updateUiState { copy(list = it) }
        }.launchIn(viewModelScope)
    }

    // Update state example: updateUiState { UiState(isLoading = false) }
    // or // updateUiState { copy(isLoading = false) }

    // Update effect example: emitUiEffect(UiEffect.ShowError(it.message.orEmpty()))
    // Use within a coroutine scope, e.g., viewModelScope.launch { ... }
}