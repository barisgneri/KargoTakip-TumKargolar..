package com.barisproduction.kargo.ui.splash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    navigateCargoList: () -> Unit,
) {
    uiEffect.collectWithLifecycle {}

    SplashContent(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        onAction = onAction,
        navigateCargoList = navigateCargoList
    )
}

@Composable
fun SplashContent(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    navigateCargoList: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Splash Content",
            fontSize = 24.sp,
            modifier = Modifier.clickable { navigateCargoList() }
        )
    }
}