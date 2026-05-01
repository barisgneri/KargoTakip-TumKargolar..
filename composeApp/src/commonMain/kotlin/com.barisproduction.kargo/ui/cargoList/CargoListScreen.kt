package com.barisproduction.kargo.ui.cargoList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barisproduction.kargo.common.extensions.collectWithLifecycle
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiAction
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiEffect
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiState
import com.barisproduction.kargo.ui.components.AnimAddCargoFAB
import com.barisproduction.kargo.ui.components.LoadingBar
import com.barisproduction.kargo.ui.theme.KargoTheme
import kotlinx.coroutines.flow.Flow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.ui.platform.LocalUriHandler
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.ui.cargoList.components.CargoItemCard
import com.barisproduction.kargo.ui.cargoList.components.CargoListAppBar
import com.barisproduction.kargo.ui.cargoList.components.DeleteConfirmationDialog
import com.barisproduction.kargo.ui.cargoList.components.RatingDialog
import com.barisproduction.kargo.ui.cargoList.components.SwipeButtonBackground
import com.barisproduction.kargo.ui.splash.SplashScreenPreviewProvider
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun CargoListScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navActions: CargoListNavActions
) {
    val uriHandler = LocalUriHandler.current

    uiEffect.collectWithLifecycle {
        when (it) {
            is UiEffect.ShowError -> {}

            is UiEffect.NavigateToAddNewCargo -> navActions.addNewCargoNavigation()

            is UiEffect.NavigateToTracking -> navActions.navigateToTracking(
                it.parcelName,
                it.trackingNumber
            )

            is UiEffect.NavigateToEdit -> navActions.navigateToEdit(
                it.parcelName,
                it.trackingNumber,
                it.cargoName
            )

            is UiEffect.OpenUrl -> uriHandler.openUri(it.url)
        }
    }

    if (uiState.showReviewDialog) {
        RatingDialog(onAction = onAction, selectedRating = uiState.selectedRating)
    }
    if (uiState.showDeleteConfirmationDialog) {
        DeleteConfirmationDialog(onAction = onAction)
    }
    when {
        uiState.isLoading -> LoadingBar()
        else -> CargoListContent(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
            onAction = onAction,
        )
    }

}

@Composable
fun CargoListContent(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
) {
    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        topBar = { CargoListAppBar() },
        floatingActionButton = {
            AnimAddCargoFAB(onClick = {
                onAction(UiAction.AddNewCargo)
            })
        }) { innerPadding ->
        CargoList(
            modifier = Modifier.padding(innerPadding),
            cargoList = uiState.list,
            onAction = onAction
        )

    }
}

@Composable
fun CargoList(
    modifier: Modifier = Modifier,
    cargoList: List<CargoModel>,
    onAction: (UiAction) -> Unit
) {
    if (cargoList.isEmpty()) {
        EmptyCargoView(modifier = modifier)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cargoList, key = { it.trackNo }) { cargo ->
                val dismissState = rememberSwipeToDismissBoxState(
                    initialValue = SwipeToDismissBoxValue.Settled,
                    positionalThreshold = { it * 0.25f }
                )
                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        SwipeButtonBackground(
                            dismissState = dismissState,
                            cargo = cargo,
                            onAction = onAction
                        )
                    }
                ) {
                    cargo.apply {
                        CargoItemCard(
                            title = cargoName,
                            trackingNumber = trackNo,
                            courierName = parcelName,
                            imageUrl = logo,
                            dateAdded = addDate ?: "Tarih bilgisi yok.",
                            onClick = { onAction(UiAction.NavigateToTracking(parcelName, trackNo)) }
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun EmptyCargoView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Hemen kargonu sorgula!",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Listede hiç kargo yok.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CargoListPreview(@PreviewParameter(SplashScreenPreviewProvider::class) uiState: UiState) {
    KargoTheme {
        CargoListScreen(
            uiState = uiState,
            uiEffect = emptyFlow(),
            onAction = {},
            navActions = CargoListNavActions.default
        )
    }

}