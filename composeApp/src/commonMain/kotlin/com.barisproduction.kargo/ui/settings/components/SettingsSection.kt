package com.barisproduction.kargo.ui.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.barisproduction.kargo.ui.theme.spacing

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = spacing.extraSmall)
        )
        content()
        HorizontalDivider(
            modifier = Modifier.padding(top = spacing.small),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}
