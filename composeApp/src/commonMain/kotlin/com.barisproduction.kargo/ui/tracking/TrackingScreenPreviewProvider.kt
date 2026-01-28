package com.barisproduction.kargo.ui.tracking

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class TrackingScreenPreviewProvider : PreviewParameterProvider<TrackingScreenContract.UiState> {
    override val values: Sequence<TrackingScreenContract.UiState>
        get() = sequenceOf(
            TrackingScreenContract.UiState(
                isLoading = true,
                isError = false,
                trackingUrl = null
            ),
            TrackingScreenContract.UiState(
                isLoading = false,
                isError = false,
                trackingUrl = "null"
            )
        )
}