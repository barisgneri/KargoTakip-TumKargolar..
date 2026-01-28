package com.barisproduction.kargo.ui.addCargo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiAction
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiState
import com.barisproduction.kargo.ui.components.ActionOutlineButton
import com.barisproduction.kargo.ui.theme.Dimens
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun CargoTrackingForm(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onAction: (UiAction) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(Res.string.start_with_enter_tracking_number),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(Dimens.paddingLarge))

        Text(
            text = stringResource(Res.string.tracking_number),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(Dimens.paddingSmall))
        KargoTextField(
            value = uiState.trackingNumber,
            onValueChange = { onAction(UiAction.OnTrackingNumberChange(it)) },
            placeholder = "99999999999",
            isError = uiState.trackingNumberError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(Dimens.paddingMedium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionOutlineButton(
                text = stringResource(Res.string.scan_barcode),
                icon = Icons.Outlined.QrCodeScanner,
                isEnabled = false,
                onClick = { onAction(UiAction.OnScanBarcode) },
                modifier = Modifier.weight(1f)
            )
            ActionOutlineButton(
                text = stringResource(Res.string.paste_clipboard),
                icon = Icons.Outlined.ContentPaste,
                onClick = {
                    onAction(UiAction.OnPasteClipboard)
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(Dimens.paddingLarge))

        CarrierSelectionTrigger(
            carrier = uiState.detectedCarrier,
            isError = uiState.cargoNameError,
            onClick = { onAction(UiAction.OnCarrierSelectClick) }
        )
        Spacer(modifier = Modifier.height(Dimens.paddingLarge))

        Text(
            text = stringResource(Res.string.cargo_name_optional),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(Dimens.paddingSmall))
        KargoTextField(
            value = uiState.cargoName ?: "",
            onValueChange = { onAction(UiAction.OnCargoNameChange(it)) },
            placeholder = stringResource(Res.string.placeholder_cargo_name_optional),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(Dimens.paddingSmall))
    }
}
