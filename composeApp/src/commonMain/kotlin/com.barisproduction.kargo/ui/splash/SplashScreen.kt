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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.app_name
import kargotakiptumkargolar.composeapp.generated.resources.app_preparing
import kargotakiptumkargolar.composeapp.generated.resources.btn_later
import kargotakiptumkargolar.composeapp.generated.resources.btn_update
import kargotakiptumkargolar.composeapp.generated.resources.check_connection_and_try_again
import kargotakiptumkargolar.composeapp.generated.resources.connection_error
import kargotakiptumkargolar.composeapp.generated.resources.fast_and_ads_free
import kargotakiptumkargolar.composeapp.generated.resources.logo
import kargotakiptumkargolar.composeapp.generated.resources.update_available
import kargotakiptumkargolar.composeapp.generated.resources.update_description_optional
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

    SplashContent(
        uiState = uiState
    )

    if (uiState.showUpdateDialog) {
        CargoBaseDialog(
            onDismissRequest = { 
                if (!uiState.isUpdateRequired) onAction(UiAction.OnDismissUpdateDialog) 
            },
            title = stringResource(Res.string.update_available),
            description = if (uiState.isUpdateRequired) 
                stringResource(Res.string.update_description_required)
            else 
                stringResource(Res.string.update_description_optional),
            icon = Icons.Default.SystemUpdate,
            confirmButtonText = stringResource(Res.string.btn_update),
            onConfirmClick = { onAction(UiAction.OnUpdateClick) },
            dismissButtonText = if (uiState.isUpdateRequired) null else stringResource(Res.string.btn_later),
            onDismissClick = { onAction(UiAction.OnDismissUpdateDialog) }
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
fun SplashContent(
    uiState: UiState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        BackgroundGlows()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    bitmap = imageResource(Res.drawable.logo), modifier = Modifier
                        .size(140.dp)
                        .shadow(
                            elevation = 24.dp,
                            shape = RoundedCornerShape(32.dp),
                            spotColor = MaterialTheme.colorScheme.primary,
                            ambientColor = MaterialTheme.colorScheme.primary
                        )
                        .clip(RoundedCornerShape(32.dp)), contentDescription = "Logo"
                )

                Spacer(modifier = Modifier.height(spacing.large))

                Text(
                    text = stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-1).sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(spacing.extraSmall))

                Text(
                    text = stringResource(Res.string.fast_and_ads_free),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 2.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (uiState.isLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = spacing.large)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(44.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp,
                        trackColor = MaterialTheme.colorScheme.primaryContainer
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    Text(
                        text = stringResource(Res.string.app_preparing),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))
                }
            }
        }
    }
}

@Composable
private fun BackgroundGlows() {
    val primaryColor = MaterialTheme.colorScheme.primary

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(primaryColor.copy(alpha = 0.08f), Color.Transparent),
                center = Offset(0f, 0f),
                radius = size.width / 1.5f
            ),
            center = Offset(0f, 0f),
            radius = size.width / 1.5f
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(primaryColor.copy(alpha = 0.12f), Color.Transparent),
                center = Offset(size.width, size.height),
                radius = size.width / 1.2f
            ),
            center = Offset(size.width, size.height),
            radius = size.width / 1.2f
        )
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
