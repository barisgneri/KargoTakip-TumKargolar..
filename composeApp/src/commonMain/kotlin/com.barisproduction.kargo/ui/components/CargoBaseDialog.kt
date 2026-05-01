package com.barisproduction.kargo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.barisproduction.kargo.ui.theme.spacing

@Composable
fun CargoBaseDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    icon: ImageVector? = null,
    confirmButtonText: String? = null,
    onConfirmClick: (() -> Unit)? = null,
    confirmButtonEnabled: Boolean = true,
    dismissButtonText: String? = null,
    onDismissClick: (() -> Unit)? = null,
    content: @Composable (ColumnScope.() -> Unit)? = null
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.85f)
                .padding(spacing.medium),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(bottom = spacing.medium)
                    )
                }

                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(spacing.small))
                }

                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(spacing.medium))
                }

                if (content != null) {
                    content()
                    Spacer(modifier = Modifier.height(spacing.medium))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing.small)
                ) {
                    if (dismissButtonText != null && onDismissClick != null) {
                        CargoButton(
                            text = dismissButtonText,
                            onClick = onDismissClick,
                            style = CargoButtonStyle.SECONDARY,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (confirmButtonText != null && onConfirmClick != null) {
                        CargoButton(
                            text = confirmButtonText,
                            onClick = onConfirmClick,
                            style = CargoButtonStyle.PRIMARY,
                            enabled = confirmButtonEnabled,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
