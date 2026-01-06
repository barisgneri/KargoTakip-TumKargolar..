package com.barisproduction.kargo.ui.cargoList

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class CargoListScreenPreviewProvider() : PreviewParameterProvider<CargoListContract.UiState> {
    override val values: Sequence<CargoListContract.UiState>
        get() = sequenceOf(
            CargoListContract.UiState(),
            CargoListContract.UiState(isLoading = true),
            CargoListContract.UiState(list = listOf())
        )
}