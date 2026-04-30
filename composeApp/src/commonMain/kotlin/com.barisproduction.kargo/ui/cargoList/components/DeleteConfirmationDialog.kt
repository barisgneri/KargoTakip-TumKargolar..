package com.barisproduction.kargo.ui.cargoList.components

import androidx.compose.runtime.Composable
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiAction
import com.barisproduction.kargo.ui.components.CargoBaseDialog
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.cancel
import kargotakiptumkargolar.composeapp.generated.resources.delete
import kargotakiptumkargolar.composeapp.generated.resources.delete_cargo_description
import kargotakiptumkargolar.composeapp.generated.resources.delete_cargo_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteConfirmationDialog(
    onAction: (UiAction) -> Unit
) {
    CargoBaseDialog(
        onDismissRequest = { onAction(UiAction.DismissDeleteDialog) },
        title = stringResource(Res.string.delete_cargo_title),
        description = stringResource(Res.string.delete_cargo_description),
        confirmButtonText = stringResource(Res.string.delete),
        onConfirmClick = { onAction(UiAction.ConfirmDelete) },
        dismissButtonText = stringResource(Res.string.cancel),
        onDismissClick = { onAction(UiAction.DismissDeleteDialog) }
    )
}
