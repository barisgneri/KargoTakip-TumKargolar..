package com.barisproduction.kargo.ui.saveDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.barisproduction.kargo.common.extensions.collectWithLifecycle
import com.barisproduction.kargo.ui.addCargo.components.CarrierSelectionSheet
import com.barisproduction.kargo.ui.addCargo.components.CarrierSelectionTrigger
import com.barisproduction.kargo.ui.components.CargoButton
import com.barisproduction.kargo.ui.components.CargoButtonStyle
import com.barisproduction.kargo.ui.components.CargoTextField
import com.barisproduction.kargo.ui.theme.spacing
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.add_new_cargo
import kargotakiptumkargolar.composeapp.generated.resources.btn_save
import kargotakiptumkargolar.composeapp.generated.resources.cancel
import kargotakiptumkargolar.composeapp.generated.resources.cargo_company
import kargotakiptumkargolar.composeapp.generated.resources.cargo_name
import kargotakiptumkargolar.composeapp.generated.resources.cargo_name_optional
import kargotakiptumkargolar.composeapp.generated.resources.tracking_number
import kargotakiptumkargolar.composeapp.generated.resources.update
import kargotakiptumkargolar.composeapp.generated.resources.update_cargo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun CargoSaveDialog(
    viewModel: CargoDialogViewModel,
    onDismissRequest: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = viewModel.uiEffect
    val onAction = viewModel::onAction

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    uiEffect.collectWithLifecycle {
        when (it) {
            is CargoDialogContract.UiEffect.Dismiss -> onDismissRequest()
            is CargoDialogContract.UiEffect.ShowMessage -> {
                scope.launch {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    if (uiState.isCarrierSelectionVisible) {
        CarrierSelectionSheet(
            parcelList = uiState.parcelList,
            onDismissRequest = { onAction(CargoDialogContract.UiAction.OnCarrierSelectDismiss) },
            onCarrierSelected = { onAction(CargoDialogContract.UiAction.OnCarrierSelected(it)) }
        )
    }

    CargoDialogContent(
        onDismissRequest = onDismissRequest,
        uiState = uiState,
        onAction = onAction
    )
}

@Composable
private fun CargoDialogContent(
    onDismissRequest: () -> Unit,
    uiState: CargoDialogContract.UiState,
    onAction: (CargoDialogContract.UiAction) -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f) // Ekranın %90
                .padding(spacing.medium),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.large)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (!uiState.isEditMode) stringResource(Res.string.add_new_cargo) else stringResource(
                            Res.string.update_cargo
                        ),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Kapat",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(spacing.medium))

                Text(
                    text = stringResource(Res.string.tracking_number),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                CargoTextField(
                    value = uiState.trackingNumber,
                    onValueChange = {
                        onAction(
                            CargoDialogContract.UiAction.OnTrackingNumberChange(
                                it
                            )
                        )
                    },
                    isError = uiState.isTrackingNumberError,
                    placeholder = "Örn: TR123456789",
                    leadingIcon = Icons.Default.QrCodeScanner,
                    enabled = !uiState.isEditMode
                )

                Spacer(modifier = Modifier.height(spacing.medium))

                Text(
                    text = stringResource(Res.string.cargo_company),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )

                CarrierSelectionTrigger(
                    carrier = uiState.detectedCarrier,
                    isError = uiState.isParcelError,
                    onClick = { onAction(CargoDialogContract.UiAction.OnCarrierSelectClick) }
                )

                Spacer(modifier = Modifier.height(spacing.medium))

                Text(
                    text = stringResource(Res.string.cargo_name),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                CargoTextField(
                    value = uiState.cargoName ?: "",
                    onValueChange = { onAction(CargoDialogContract.UiAction.OnCargoNameChange(it)) },
                    placeholder = "Örn: Hediye",
                    isError = uiState.isCargoNameError,
                    leadingIcon = Icons.Default.Label
                )

                Spacer(modifier = Modifier.height(spacing.extraLarge))

                CargoButton(
                    text = if (!uiState.isEditMode) stringResource(Res.string.btn_save) else stringResource(
                        Res.string.update
                    ),
                    style = CargoButtonStyle.PRIMARY,
                    onClick = {
                        onAction(CargoDialogContract.UiAction.OnSaveClick)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(spacing.small))

                CargoButton(
                    text = stringResource(Res.string.cancel),
                    style = CargoButtonStyle.SECONDARY,
                    onClick = onDismissRequest,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
