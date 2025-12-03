package com.barisproduction.kargo.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.barisproduction.kargo.ui.splash.SplashScreen
import com.barisproduction.kargo.ui.splash.SplashViewModel
import com.barisproduction.kargo.navigation.Screen.Splash
import com.barisproduction.kargo.ui.cargoList.CargoListScreen
import com.barisproduction.kargo.ui.cargoList.CargoListViewModel
import com.barisproduction.kargo.navigation.Screen.CargoList
import com.barisproduction.kargo.ui.savedCargo.SavedCargoScreen
import com.barisproduction.kargo.ui.savedCargo.SavedCargoViewModel
import com.barisproduction.kargo.navigation.Screen.SavedCargo

private const val DURATION = 1000

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: Screen,
    modifier: Modifier = Modifier,
) {
    val enterAnim = fadeIn(tween(DURATION))
    val exitAnim = fadeOut(tween(DURATION))

    NavHost(
        modifier = Modifier.then(modifier),
        navController = navController,
        startDestination = startDestination,
        enterTransition = { enterAnim },
        exitTransition = { exitAnim },
        popEnterTransition = { enterAnim },
        popExitTransition = { exitAnim },
    ) {
        composable<Splash> {
            val viewModel = koinViewModel<SplashViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SplashScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navigateCargoList = { navController.navigate(CargoList) }
            )
        }
        composable<CargoList> {
            val viewModel = koinViewModel<CargoListViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            CargoListScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable<SavedCargo> {
            val viewModel = koinViewModel<SavedCargoViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SavedCargoScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
    }
}