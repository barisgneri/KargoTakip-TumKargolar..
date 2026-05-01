package com.barisproduction.kargo.ui.tracking

object TrackingScreenContract {
    data class UiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val trackingUrl: String? = null,
        val logo: String? = null,
        val js: String = "",
        val saveButtonVisibility: Boolean = true,
        val showSaveConfirmationDialog: Boolean = false,
    )
    sealed class UiEffect{
        data class ShowError(val message: String) : UiEffect()
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateBack : UiEffect()
        data class ShowSaveDialog(val parcelName: String, val trackingNo: String) : UiEffect()
    }
    sealed class UiAction{
        data object OnBackClick : UiAction()
        data object OnSaveClick : UiAction()
        data class OnLoadingStateChanged(val isLoading: Boolean) : UiAction()
        data class OnErrorReceived(val errorCode: Int) : UiAction()
        data object OnRetryClick : UiAction()
        data object OnDismissSaveDialog : UiAction()
        data object OnExitWithoutSaving : UiAction()
    }
}
