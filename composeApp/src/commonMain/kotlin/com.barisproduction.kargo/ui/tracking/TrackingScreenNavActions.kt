package com.barisproduction.kargo.ui.tracking

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barisproduction.kargo.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel

class TrackingScreenNavActions(val onBack: () -> Unit = {}, val onSave: (String, String) -> Unit) {
    companion object {
        val default: TrackingScreenNavActions
            get() = TrackingScreenNavActions(
                onBack = {},
                onSave = { _, _ -> }
            )
    }
}

fun NavGraphBuilder.trackingScreen(actions: TrackingScreenNavActions) {
    composable<Screen.Tracking> {
        val viewModel: TrackingViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        TrackingScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            navActions = actions
        )
    }
}