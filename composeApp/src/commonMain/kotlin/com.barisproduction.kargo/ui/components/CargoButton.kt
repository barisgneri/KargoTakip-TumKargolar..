package com.barisproduction.kargo.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CargoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: CargoButtonStyle = CargoButtonStyle.PRIMARY,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val buttonHeight = 56.dp
    val baseModifier = modifier.height(buttonHeight)
    val shape = MaterialTheme.shapes.medium

    when (style) {
        CargoButtonStyle.PRIMARY -> {
            Button(
                onClick = onClick,
                modifier = baseModifier,
                enabled = enabled && !isLoading,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                )
            ) {
                ButtonContent(text = text, icon = icon, isLoading = isLoading)
            }
        }

        CargoButtonStyle.SECONDARY -> {
            Button(
                onClick = onClick,
                modifier = baseModifier,
                enabled = enabled && !isLoading,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                ButtonContent(text = text, icon = icon, isLoading = isLoading)
            }
        }

        CargoButtonStyle.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                modifier = baseModifier,
                enabled = enabled && !isLoading,
                shape = shape,
            ) {
                ButtonContent(text = text, icon = icon, isLoading = isLoading)
            }
        }
    }
}

@Composable
private fun ButtonContent(
    text: String,
    icon: ImageVector?,
    isLoading: Boolean
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            strokeWidth = 2.dp
        )
    } else {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

enum class CargoButtonStyle {
    PRIMARY,
    SECONDARY,
    OUTLINED
}