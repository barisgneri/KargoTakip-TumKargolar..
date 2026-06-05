package com.barisproduction.kargo.ui.splash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.window.Dialog
import com.barisproduction.kargo.common.extensions.collectWithLifecycle
import com.barisproduction.kargo.ui.components.CargoBaseDialog
import com.barisproduction.kargo.ui.components.ErrorView
import com.barisproduction.kargo.ui.splash.SplashContract.UiAction
import com.barisproduction.kargo.ui.splash.SplashContract.UiEffect
import com.barisproduction.kargo.ui.splash.SplashContract.UiState
import com.barisproduction.kargo.ui.theme.KargoTheme
import com.barisproduction.kargo.ui.theme.spacing
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.Lottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.app_name
import kargotakiptumkargolar.composeapp.generated.resources.app_preparing
import kargotakiptumkargolar.composeapp.generated.resources.btn_update
import kargotakiptumkargolar.composeapp.generated.resources.check_connection_and_try_again
import kargotakiptumkargolar.composeapp.generated.resources.connection_error
import kargotakiptumkargolar.composeapp.generated.resources.fast_and_ads_free
import kargotakiptumkargolar.composeapp.generated.resources.logo
import kargotakiptumkargolar.composeapp.generated.resources.update_available
import kargotakiptumkargolar.composeapp.generated.resources.update_description_required
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.imageResource
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
    val uriHandler = LocalUriHandler.current
    uiEffect.collectWithLifecycle {
        when (it) {
            UiEffect.NavigateToMain -> navActions.navigateToMain()
            is UiEffect.NavigateToStore -> uriHandler.openUri(it.url)
        }
    }

    SplashContent()

    if (uiState.showUpdateDialog) {
        CargoBaseDialog(
            onDismissRequest = {},
            title = stringResource(Res.string.update_available),
            description = stringResource(Res.string.update_description_required),
            icon = Icons.Default.SystemUpdate,
            confirmButtonText = stringResource(Res.string.btn_update),
            onConfirmClick = { onAction(UiAction.OnUpdateClick) }
        )
    }

    if (uiState.errorMessage != null) {
        Dialog(onDismissRequest = {}) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                ErrorView(
                    modifier = Modifier.height(300.dp),
                    message = stringResource(Res.string.check_connection_and_try_again),
                    title = stringResource(Res.string.connection_error),
                    onRetry = { onAction(UiAction.Retry) }
                )
            }
        }
    }
}

@Composable
fun SplashContent() {

    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/splash-anim.json").decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition = composition
    )

    val painter = rememberLottiePainter(
        composition = composition,
        progress = { progress }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF101622)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = spacing.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Lottie(
                painter = painter,
                contentDescription = "Lottie Animation",
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview(@PreviewParameter(SplashScreenPreviewProvider::class) uiState: UiState) {
    KargoTheme {
        SplashScreen(
            uiState = uiState,
            uiEffect = emptyFlow(),
            onAction = {},
            navActions = SplashNavActions.default
        )
    }
}
