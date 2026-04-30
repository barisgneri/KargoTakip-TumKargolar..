package com.barisproduction.kargo.ui.splash

object SplashContract {
    data class UiState(
        val isLoading: Boolean = true,
        val errorMessage: String? = null,
        val list: List<String> = emptyList(),
        val showUpdateDialog: Boolean = false,
        val isUpdateRequired: Boolean = false
    )

    sealed class UiAction {
        data object CheckNetwork : UiAction()
        data object OnUpdateClick : UiAction()
        data object OnDismissUpdateDialog : UiAction()
    }
    sealed class UiEffect {
        data object NavigateToMain : UiEffect()
        data object NavigateToStore : UiEffect()
    }
}