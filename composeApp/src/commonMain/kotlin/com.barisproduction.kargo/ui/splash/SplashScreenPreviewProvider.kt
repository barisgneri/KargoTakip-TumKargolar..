package com.barisproduction.kargo.ui.splash

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class SplashScreenPreviewProvider() : PreviewParameterProvider<SplashContract.UiState> {
    override val values: Sequence<SplashContract.UiState>
        get() = sequenceOf(
            SplashContract.UiState(),
            SplashContract.UiState(isLoading = true),
            SplashContract.UiState(errorMessage = "Hata", isLoading = false)
        )
}