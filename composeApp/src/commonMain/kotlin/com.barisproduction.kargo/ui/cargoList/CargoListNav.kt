package com.barisproduction.kargo.ui.cargoList

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barisproduction.kargo.domain.model.ParcelModel
import com.barisproduction.kargo.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel

class CargoListNavActions(
    val addNewCargoNavigation : () -> Unit,
    val navigateToTracking : (ParcelModel, String) -> Unit
){
    companion object {
        val default: CargoListNavActions
            get() =CargoListNavActions(
                addNewCargoNavigation = {},
                navigateToTracking = { _, _ -> }
            )
    }
}

fun NavGraphBuilder.cargoListScreen(actions: CargoListNavActions){
    composable<Screen.CargoList> {
        val viewModel: CargoListViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        CargoListScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            navActions = actions
        )
    }
}