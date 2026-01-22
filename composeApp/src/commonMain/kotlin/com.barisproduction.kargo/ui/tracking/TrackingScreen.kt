package com.barisproduction.kargo.ui.tracking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.barisproduction.kargo.common.collectWithLifecycle
import com.barisproduction.kargo.ui.components.ErrorView
import com.barisproduction.kargo.ui.components.KargoWebView
import com.barisproduction.kargo.ui.components.LoadingBar
import com.barisproduction.kargo.ui.theme.KargoTheme
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiAction
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiEffect
import com.barisproduction.kargo.ui.tracking.TrackingScreenContract.UiState
import com.barisproduction.kargo.ui.tracking.components.TrackingScreenTopBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun TrackingScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navActions: TrackingScreenNavActions
) {

    uiEffect.collectWithLifecycle { uiEffect ->
        when (uiEffect) {
            is UiEffect.NavigateBack -> navActions.onBack()
            is UiEffect.ShowError -> {}
        }
    }

    TrackingScreenContent(
        uiState = uiState,
        onAction = onAction
    )
}

@Composable
fun TrackingScreenContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit
) {
    Scaffold(
        topBar = {
            TrackingScreenTopBar(
                onBack = { onAction(UiAction.OnBackClick) },
                onSave = { onAction(UiAction.OnSaveClick) }
            )
        }
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
                    KargoWebView(
                        url = url,
                        modifier = Modifier.fillMaxSize(),
                        onLoadingStateChanged = { onAction(UiAction.OnLoadingStateChanged(it)) },
                        onError = { onAction(UiAction.OnErrorReceived(it)) }
                    )
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
