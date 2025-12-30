package com.barisproduction.kargo.ui.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.barisproduction.kargo.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel

data class SplashNavActions(
    val navigateToMain : () -> Unit,
){
    companion object {
        val default: SplashNavActions
            get() =SplashNavActions(
                navigateToMain = {}
            )
    }
}

fun NavGraphBuilder.splashScreen(actions: SplashNavActions){
    composable<Screen.Splash> {

        val viewModel: SplashViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        SplashScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            navActions = actions
        )
    }
}