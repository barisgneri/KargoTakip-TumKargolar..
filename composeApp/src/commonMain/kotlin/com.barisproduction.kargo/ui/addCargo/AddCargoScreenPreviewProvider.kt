package com.barisproduction.kargo.ui.addCargo

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class AddCargoScreenPreviewProvider() : PreviewParameterProvider<AddCargoContract.UiState> {
    override val values: Sequence<AddCargoContract.UiState>
        get() = sequenceOf(
            AddCargoContract.UiState(),
            AddCargoContract.UiState(cargoNameError = true, trackingNumberError = true),
        )
}