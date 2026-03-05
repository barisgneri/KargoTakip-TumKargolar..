package com.barisproduction.kargo.ui.addCargo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.barisproduction.kargo.common.extensions.collectWithLifecycle
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiAction
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiEffect
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiState
import com.barisproduction.kargo.ui.addCargo.components.AddCargoTopBar
import com.barisproduction.kargo.ui.addCargo.components.CargoTrackingForm
import com.barisproduction.kargo.ui.addCargo.components.CarrierSelectionSheet
import com.barisproduction.kargo.ui.components.CargoButton
import com.barisproduction.kargo.ui.theme.KargoTheme
import com.barisproduction.kargo.ui.theme.spacing
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun AddCargoScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navActions: AddCargoNavActions
) {
    uiEffect.collectWithLifecycle {
        when (it) {
            is UiEffect.NavigateBack -> navActions.onBack()
            is UiEffect.NavigateToSearch -> navActions.navigateToSearch(it.cargoName, it.trackingNo)
            is UiEffect.ShowToast -> {}
            is UiEffect.ShowSaveDialog -> navActions.onSave(it.parcelName, it.trackingNo)
        }
    }

    AddCargoScreenContent(uiState, onAction)

    if (uiState.isCarrierSelectionVisible) {
        CarrierSelectionSheet(
            parcelList = uiState.parcelList,
            onDismissRequest = { onAction(UiAction.OnCarrierSelectDismiss) },
            onCarrierSelected = { onAction(UiAction.OnCarrierSelected(it)) }
        )
    }
}

@Composable
private fun AddCargoScreenContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            AddCargoTopBar(
                onBack = { onAction(UiAction.OnBackClick) },
                onSave = { onAction(UiAction.OnSaveClick) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CargoTrackingForm(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = spacing.medium),
                uiState = uiState,
                onAction = onAction
            )

            Column() {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(spacing.medium))
                CargoButton(
                    onClick = { onAction(UiAction.OnSearchCargoClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .padding(horizontal = spacing.medium),
                    text = stringResource(Res.string.search_cargo),
                    icon = Icons.Default.Search
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddCargoScreenPreview(@PreviewParameter(AddCargoScreenPreviewProvider::class) uiState: UiState) {
    KargoTheme {
        AddCargoScreen(uiState, emptyFlow(), {}, AddCargoNavActions.default)
    }
}
