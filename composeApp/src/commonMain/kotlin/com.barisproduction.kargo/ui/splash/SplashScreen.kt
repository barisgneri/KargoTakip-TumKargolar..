package com.barisproduction.kargo.ui.splash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barisproduction.kargo.common.collectWithLifecycle
import com.barisproduction.kargo.ui.components.EmptyScreen
import com.barisproduction.kargo.ui.components.LoadingBar
import com.barisproduction.kargo.ui.splash.SplashContract.UiAction
import com.barisproduction.kargo.ui.splash.SplashContract.UiEffect
import com.barisproduction.kargo.ui.splash.SplashContract.UiState
import kotlinx.coroutines.flow.Flow

@Composable
fun SplashScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navActions: SplashNavActions,
) {
    uiEffect.collectWithLifecycle {
        when(it){
            UiEffect.NavigateToMain -> navActions.navigateToMain()
        }
    }

    SplashContent(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        onAction = onAction,
        navActions = navActions
    )

    if (uiState.isError) {
        androidx.compose.ui.window.Dialog(onDismissRequest = {}) {
            androidx.compose.material3.Surface(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                color = androidx.compose.material3.MaterialTheme.colorScheme.surface
            ) {
                 com.barisproduction.kargo.ui.components.ErrorView(
                    modifier = Modifier.height(300.dp),
                    message = "İnternet bağlantınızı kontrol edip tekrar deneyin.",
                    title = "Bağlantı Hatası",
                    onRetry = { onAction(UiAction.CheckNetwork) }
                )
            }
        }
    }
}

@Composable
fun SplashContent(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    navActions: SplashNavActions
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (uiState.isLoading) {
             LoadingBar()
        } 
    }
}