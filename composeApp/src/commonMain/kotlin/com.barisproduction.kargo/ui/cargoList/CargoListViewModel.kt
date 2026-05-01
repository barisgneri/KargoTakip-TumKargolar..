package com.barisproduction.kargo.ui.cargoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.common.feedback.buildFeedbackMailUri
import com.barisproduction.kargo.common.feedback.getStoreReviewUri
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.domain.usecase.GetReviewCompletedUseCase
import com.barisproduction.kargo.domain.usecase.DeleteCargoUseCase
import com.barisproduction.kargo.domain.usecase.GetCargosUseCase
import com.barisproduction.kargo.domain.usecase.SetReviewCompletedUseCase
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiAction
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiEffect
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CargoListViewModel(
    private val getCargosUseCase: GetCargosUseCase,
    private val deleteCargoUseCase: DeleteCargoUseCase,
    private val getReviewCompletedUseCase: GetReviewCompletedUseCase,
    private val setReviewCompletedUseCase: SetReviewCompletedUseCase
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    private var previousCargoCount: Int? = null
    private var isReviewCompleted: Boolean = false
    private var isReviewDismissedForSession: Boolean = false

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is UiAction.AddNewCargo -> emitUiEffect(UiEffect.NavigateToAddNewCargo)
                is UiAction.NavigateToTracking -> emitUiEffect(UiEffect.NavigateToTracking(uiAction.parcelName, uiAction.trackingNumber))
                is UiAction.RequestDelete -> {
                    updateUiState {
                        copy(
                            showDeleteConfirmationDialog = true,
                            pendingDeleteTrackNo = uiAction.trackNo
                        )
                    }
                }
                is UiAction.ConfirmDelete -> {
                    val trackNo = uiState.value.pendingDeleteTrackNo ?: return@launch
                    deleteCargoUseCase(trackNo)
                    updateUiState {
                        copy(
                            showDeleteConfirmationDialog = false,
                            pendingDeleteTrackNo = null
                        )
                    }
                }
                is UiAction.DismissDeleteDialog -> {
                    updateUiState {
                        copy(
                            showDeleteConfirmationDialog = false,
                            pendingDeleteTrackNo = null
                        )
                    }
                }
                is UiAction.EditCargo -> emitUiEffect(UiEffect.NavigateToEdit(uiAction.parcelName, uiAction.trackingNumber, uiAction.cargoName))
                is UiAction.OnReviewRatingChanged -> {
                    updateUiState { copy(selectedRating = uiAction.rating) }
                }
                is UiAction.OnReviewDismiss -> {
                    isReviewDismissedForSession = true
                    updateUiState { copy(showReviewDialog = false, selectedRating = 0) }
                }
                is UiAction.OnReviewConfirm -> {
                    val rating = uiState.value.selectedRating
                    if (rating == 0) return@launch

                    setReviewCompletedUseCase(true)
                    isReviewCompleted = true
                    updateUiState { copy(showReviewDialog = false, selectedRating = 0) }
                    if (rating >= REVIEW_STORE_THRESHOLD) {
                        emitUiEffect(UiEffect.OpenUrl(getStoreReviewUri()))
                    } else {
                        emitUiEffect(UiEffect.OpenUrl(buildFeedbackMailUri()))
                    }
                }
            }
        }
    }

    init {
        updateUiState { copy(isLoading = true) }
        viewModelScope.launch {
            // Dialog kararını kaçırmamak için önce review durumunu yükle.
            isReviewCompleted = getReviewCompletedUseCase()
            getCargosUseCase().onEach {
                updateUiState { copy(list = it, isLoading = false) }
                maybeShowReviewDialog(currentCargoCount = it.size)
            }.launchIn(viewModelScope)
        }
    }

    private fun maybeShowReviewDialog(currentCargoCount: Int) {
        val previousCount = previousCargoCount
        previousCargoCount = currentCargoCount

        if (previousCount == null) return

        val hasNewCargoAdded = currentCargoCount > previousCount
        val shouldShowDialog = hasNewCargoAdded &&
            currentCargoCount >= MIN_CARGO_COUNT_FOR_REVIEW &&
            !isReviewCompleted &&
            !isReviewDismissedForSession

        if (shouldShowDialog) {
            updateUiState { copy(showReviewDialog = true) }
        }
    }

    private companion object {
        const val MIN_CARGO_COUNT_FOR_REVIEW = 2
        const val REVIEW_STORE_THRESHOLD = 4
    }
}