package com.barisproduction.kargo.ui.savedCargo

object SavedCargoContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
    )

    sealed class UiAction
    sealed class UiEffect
}