package com.barisproduction.kargo.ui.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NetworkLocked
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.barisproduction.kargo.common.extensions.collectWithLifecycle
import com.barisproduction.kargo.ui.components.CargoButton
import com.barisproduction.kargo.ui.components.CargoButtonStyle
import com.barisproduction.kargo.ui.components.ErrorView
import com.barisproduction.kargo.ui.components.KargoWebView
import com.barisproduction.kargo.ui.components.LoadingBar
import com.barisproduction.kargo.ui.theme.KargoTheme
import com.barisproduction.kargo.ui.theme.spacing
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiAction
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiEffect
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiState
import com.barisproduction.kargo.ui.tracking.components.TrackingScreenTopBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun TrackingScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navActions: TrackingScreenNavActions
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    uiEffect.collectWithLifecycle { uiEffect ->
        when (uiEffect) {
            is UiEffect.NavigateBack -> navActions.onBack()
            is UiEffect.ShowToast -> {
                scope.launch {
                    snackbarHostState.showSnackbar(uiEffect.message)
                }
            }

            is UiEffect.ShowError -> {}
            is UiEffect.ShowSaveDialog -> navActions.onSave(
                uiEffect.parcelName,
                uiEffect.trackingNo
            )
        }
    }

    TrackingScreenContent(
        uiState = uiState,
        onAction = onAction,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun TrackingScreenContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            TrackingScreenTopBar(
                onBack = { onAction(UiAction.OnBackClick) },
                onSave = { onAction(UiAction.OnSaveClick) },
                saveButtonVisibility = uiState.saveButtonVisibility
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isError) {
                ErrorView(
                    message = uiState.errorMessage ?: "Bir hata oluştu",
                    onRetry = { onAction(UiAction.OnRetryClick) }
                )
            } else {
                uiState.trackingUrl?.let { url ->
                    Column(
                        modifier = Modifier.padding(spacing.medium)
                            .clip(MaterialTheme.shapes.medium).border(
                                1.dp,
                                MaterialTheme.colorScheme.outline,
                                MaterialTheme.shapes.medium
                            ).background(MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(spacing.small),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Security",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(spacing.small))
                            Text(
                                text = url,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        KargoWebView(
                            url = url,
                            modifier = Modifier.fillMaxSize(),
                            onLoadingStateChanged = { onAction(UiAction.OnLoadingStateChanged(it)) },
                            onError = { onAction(UiAction.OnErrorReceived(it)) },
                            js = uiState.js
                        )
                    }
                }
            }

            if (uiState.isLoading) {
                LoadingBar()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TrackingScreenPreview(@PreviewParameter(TrackingScreenPreviewProvider::class) uiState: UiState) {
    KargoTheme {
        TrackingScreen(
            uiState = uiState,
            uiEffect = emptyFlow(),
            onAction = {},
            navActions = TrackingScreenNavActions.default
        )
    }
}
