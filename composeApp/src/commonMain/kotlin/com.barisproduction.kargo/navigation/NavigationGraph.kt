package com.barisproduction.kargo.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.barisproduction.kargo.ui.addCargo.AddCargoNavActions
import com.barisproduction.kargo.ui.addCargo.addCargoScreen
import com.barisproduction.kargo.ui.cargoList.CargoListNavActions
import com.barisproduction.kargo.ui.cargoList.cargoListScreen
import com.barisproduction.kargo.ui.splash.SplashNavActions
import com.barisproduction.kargo.ui.splash.splashScreen
import com.barisproduction.kargo.ui.tracking.TrackingScreenNavActions
import com.barisproduction.kargo.ui.tracking.trackingScreen

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
        splashScreen(actions = SplashNavActions(navigateToMain = {
            navController.navigate(Screen.CargoList) {
                popUpTo(Screen.Splash) {
                    inclusive = true
                }
            }
        }))
        cargoListScreen(
            actions = CargoListNavActions(
                addNewCargoNavigation = {
                    navController.navigate(Screen.AddNewCargo)
                })
        )
        addCargoScreen(actions = AddCargoNavActions(onBack = {
            navController.popBackStack()
        }, navigateToSearch = { cargo, no ->
            navController.navigate(Screen.Tracking(cargoParcel = cargo, trackingNumber = no)) {
                popUpTo(Screen.AddNewCargo) {
                    inclusive = true
                }
            }
        }))
        trackingScreen(
            actions = TrackingScreenNavActions(
                onBack = {
                    navController.popBackStack()
                }
            )
        )
    }
}