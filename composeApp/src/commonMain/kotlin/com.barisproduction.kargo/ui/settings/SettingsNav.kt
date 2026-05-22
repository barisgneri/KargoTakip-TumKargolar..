package com.barisproduction.kargo.ui.settings

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barisproduction.kargo.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel

data class SettingsNavActions(
    val onBack: () -> Unit
)

fun NavGraphBuilder.settingsScreen(actions: SettingsNavActions) {
    composable<Screen.Settings> {
        val viewModel: SettingsViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        
        SettingsScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            actions = actions
        )
    }
}
