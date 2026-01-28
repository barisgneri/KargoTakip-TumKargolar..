package com.barisproduction.kargo.ui.addCargo

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barisproduction.kargo.domain.model.ParcelModel
import com.barisproduction.kargo.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel

class AddCargoNavActions(
    val onBack: () -> Unit,
    val navigateToSearch: (ParcelModel, String) -> Unit
) {
    companion object {
        val default: AddCargoNavActions
            get() = AddCargoNavActions(
                onBack = {},
                navigateToSearch = {a,b->}
            )
    }
}

fun NavGraphBuilder.addCargoScreen(actions: AddCargoNavActions) {
    composable<Screen.AddNewCargo> {
        val viewModel: AddCargoViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        
        AddCargoScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            navActions = actions
        )
    }
}
