package com.barisproduction.kargo.ui.splash

object SplashContract {
    data class UiState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val list: List<String> = emptyList(),
    )

    sealed class UiAction {
        data object CheckNetwork : UiAction()
    }
    sealed class UiEffect {
        data object NavigateToMain : UiEffect()
    }
}