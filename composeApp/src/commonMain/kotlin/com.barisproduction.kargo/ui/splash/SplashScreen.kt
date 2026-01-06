package com.barisproduction.kargo.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.barisproduction.kargo.common.collectWithLifecycle
import com.barisproduction.kargo.ui.components.ErrorView
import com.barisproduction.kargo.ui.components.LoadingBar
import com.barisproduction.kargo.ui.splash.SplashContract.UiAction
import com.barisproduction.kargo.ui.splash.SplashContract.UiEffect
import com.barisproduction.kargo.ui.splash.SplashContract.UiState
import com.barisproduction.kargo.ui.theme.KargoTheme
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.check_connection_and_try_again
import kargotakiptumkargolar.composeapp.generated.resources.connection_error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

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
        Dialog(onDismissRequest = {}) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                 ErrorView(
                    modifier = Modifier.height(300.dp),
                    message = stringResource(Res.string.check_connection_and_try_again),
                    title = stringResource(Res.string.connection_error),
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

@Preview(showBackground = true)
@Composable
private fun SplashPreview(@PreviewParameter(SplashScreenPreviewProvider::class) uiState: UiState){
    KargoTheme {
        SplashScreen(
            uiState = uiState,
            uiEffect = emptyFlow(),
            onAction = {},
            navActions = SplashNavActions.default
        )
    }
}