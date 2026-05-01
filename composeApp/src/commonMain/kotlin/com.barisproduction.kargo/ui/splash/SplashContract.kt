package com.barisproduction.kargo.ui.splash

object SplashContract {
    data class UiState(
        val isLoading: Boolean = true,
        val errorMessage: String? = null,
        val list: List<String> = emptyList(),
        val showUpdateDialog: Boolean = false,
        val storeUrl: String? = null
    )

    sealed class UiAction {
        data object Retry : UiAction()
        data object OnUpdateClick : UiAction()
    }
    sealed class UiEffect {
        data object NavigateToMain : UiEffect()
        data class NavigateToStore(val url: String) : UiEffect()
    }
}