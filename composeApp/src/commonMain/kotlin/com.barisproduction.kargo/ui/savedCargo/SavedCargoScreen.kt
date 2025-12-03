package com.barisproduction.kargo.ui.savedCargo

import kotlinx.coroutines.flow.Flow
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.barisproduction.kargo.common.collectWithLifecycle

import com.barisproduction.kargo.ui.savedCargo.SavedCargoContract.UiAction
import com.barisproduction.kargo.ui.savedCargo.SavedCargoContract.UiEffect
import com.barisproduction.kargo.ui.savedCargo.SavedCargoContract.UiState

@Composable
fun SavedCargoScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
) {
    uiEffect.collectWithLifecycle {}

    SavedCargoContent(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        onAction = onAction,
    )
}

@Composable
fun SavedCargoContent(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Saved Cargo Content",
            fontSize = 24.sp,
        )
    }
}