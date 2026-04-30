package com.barisproduction.kargo.ui.cargoList

import com.barisproduction.kargo.domain.model.CargoModel

object CargoListContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<CargoModel> = emptyList(),
        val showReviewDialog: Boolean = false,
        val selectedRating: Int = 0,
    )

    sealed class UiAction{
        data object AddNewCargo : UiAction()
        data class NavigateToTracking(val parcelName: String, val trackingNumber: String) : UiAction()
        data class DeleteCargo(val trackNo: String) : UiAction()
        data class EditCargo(val parcelName: String, val trackingNumber: String, val cargoName: String) : UiAction()
        data class OnReviewRatingChanged(val rating: Int) : UiAction()
        data object OnReviewDismiss : UiAction()
        data object OnReviewConfirm : UiAction()
    }
    sealed class UiEffect{
        data class ShowError(val message: String) : UiEffect()
        data object NavigateToAddNewCargo : UiEffect()
        data class NavigateToTracking(val parcelName: String, val trackingNumber: String) : UiEffect()
        data class NavigateToEdit(val parcelName: String, val trackingNumber: String, val cargoName: String) : UiEffect()
        data class OpenUrl(val url: String) : UiEffect()

    }
}