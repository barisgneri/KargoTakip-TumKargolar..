package com.barisproduction.kargo.ui.tracking

object TrackingScreenContract {
    data class UiState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String? = null,
        val trackingUrl: String? = null,
        val logo: String? = null
    )
    sealed class UiEffect{
        data class ShowError(val message: String) : UiEffect()
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateBack : UiEffect()
    }
    sealed class UiAction{
        data object OnBackClick : UiAction()
        data object OnSaveClick : UiAction()
        data class OnLoadingStateChanged(val isLoading: Boolean) : UiAction()
        data class OnErrorReceived(val message: String) : UiAction()
        data object OnRetryClick : UiAction()
    }
}
