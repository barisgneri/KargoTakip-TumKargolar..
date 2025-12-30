package com.barisproduction.kargo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable

@Composable
fun KargoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val typography = getAppTypography()
    
    // Shapes are currently default, but we pass them explicitly for future customization if needed.
    // Ensure Shapes import points to material3 defaults or your own Shapes definition if you have one.
    // If Shapes is not defined in this package, we can rely on MaterialTheme defaults by omitting it or defining it.
    // Assuming standard Material3 Shapes for now.

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}