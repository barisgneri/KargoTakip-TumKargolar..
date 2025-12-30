package com.barisproduction.kargo.ui.cargoList

import com.barisproduction.kargo.domain.model.CargoModel

object CargoListContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<CargoModel> = emptyList(),
    )

    sealed class UiAction{
        data object AddNewCargo : UiAction()
    }
    sealed class UiEffect{
        data class ShowError(val message: String) : UiEffect()
        data object NavigateToAddNewCargo : UiEffect()
    }
}