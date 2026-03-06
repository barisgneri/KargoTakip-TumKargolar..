package com.barisproduction.kargo.ui.tracking

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class TrackingScreenPreviewProvider : PreviewParameterProvider<TrackingScreenContract.UiState> {
    override val values: Sequence<TrackingScreenContract.UiState>
        get() = sequenceOf(
            TrackingScreenContract.UiState(
                isLoading = true,
                errorMessage = null,
                trackingUrl = null
            ),
            TrackingScreenContract.UiState(
                isLoading = false,
                errorMessage = null,
                trackingUrl = "null"
            ),
            TrackingScreenContract.UiState(
                isLoading = false,
                errorMessage = "Bir hata oluştu",
                trackingUrl = null
            )
        )
}