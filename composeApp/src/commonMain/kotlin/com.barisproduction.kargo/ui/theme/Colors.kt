package com.barisproduction.kargo.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// LIGHT TEMA
val cargo_theme_light_primary = Color(0xFF135BEC)
val cargo_theme_light_onPrimary = Color(0xFFFFFFFF)
val cargo_theme_light_primaryContainer = Color(0xFFE0E7FF)
val cargo_theme_light_onPrimaryContainer = Color(0xFF0F3E9C)

// Arka Plan ve Yüzeyler
val cargo_theme_light_background = Color(0xFFF6F6F8)
val cargo_theme_light_onBackground = Color(0xFF0F172A)
val cargo_theme_light_surface = Color(0xFFFFFFFF) // bg-white (Kartlar ve header)
val cargo_theme_light_onSurface = Color(0xFF0F172A)

// İkincil Yüzeyler ve Çizgiler
val cargo_theme_light_surfaceVariant = Color(0xFFF8FAFC)
val cargo_theme_light_onSurfaceVariant = Color(0xFF64748B)
val cargo_theme_light_outline = Color(0xFFE2E8F0)
val cargo_theme_light_outlineVariant = Color(0xFFDFE6EE)

// Hata Durumu (Standart M3 Kırmızısı)
val cargo_theme_light_error = Color(0xFFBA1A1A)
val cargo_theme_light_onError = Color(0xFFFFFFFF)
val cargo_theme_light_errorContainer = Color(0xFFFFDAD6)
val cargo_theme_light_onErrorContainer = Color(0xFF410002)


// DARK TEMA
val cargo_theme_dark_primary = Color(0xFF3B82F6)
val cargo_theme_dark_onPrimary = Color(0xFFFFFFFF)
val cargo_theme_dark_primaryContainer = Color(0xFF1E3A8A)
val cargo_theme_dark_onPrimaryContainer = Color(0xFFDBEAFE)

// Arka Plan ve Yüzeyler
val cargo_theme_dark_background = Color(0xFF101622)
val cargo_theme_dark_onBackground = Color(0xFFF1F5F9)
val cargo_theme_dark_surface = Color(0xFF0F172A) // Kartlar
val cargo_theme_dark_onSurface = Color(0xFFF1F5F9)

// İkincil Yüzeyler ve Çizgiler
val cargo_theme_dark_surfaceVariant = Color(0xFF1E293B)
val cargo_theme_dark_onSurfaceVariant = Color(0xFF94A3B8)
val cargo_theme_dark_outline = Color(0xFF1E293B)
val cargo_theme_dark_outlineVariant = Color(0xFF334155)

// Hata Durumu
val cargo_theme_dark_error = Color(0xFFFFB4AB)
val cargo_theme_dark_onError = Color(0xFF690005)
val cargo_theme_dark_errorContainer = Color(0xFF93000A)
val cargo_theme_dark_onErrorContainer = Color(0xFFFFDAD6)

val LightColorScheme = lightColorScheme(
    primary = cargo_theme_light_primary,
    onPrimary = cargo_theme_light_onPrimary,
    primaryContainer = cargo_theme_light_primaryContainer,
    onPrimaryContainer = cargo_theme_light_onPrimaryContainer,
    error = cargo_theme_light_error,
    errorContainer = cargo_theme_light_errorContainer,
    onError = cargo_theme_light_onError,
    onErrorContainer = cargo_theme_light_onErrorContainer,
    background = cargo_theme_light_background,
    onBackground = cargo_theme_light_onBackground,
    surface = cargo_theme_light_surface,
    onSurface = cargo_theme_light_onSurface,
    surfaceVariant = cargo_theme_light_surfaceVariant,
    onSurfaceVariant = cargo_theme_light_onSurfaceVariant,
    outline = cargo_theme_light_outline,
    outlineVariant = cargo_theme_light_outlineVariant,
)

val DarkColorScheme = darkColorScheme(
    primary = cargo_theme_dark_primary,
    onPrimary = cargo_theme_dark_onPrimary,
    primaryContainer = cargo_theme_dark_primaryContainer,
    onPrimaryContainer = cargo_theme_dark_onPrimaryContainer,
    error = cargo_theme_dark_error,
    errorContainer = cargo_theme_dark_errorContainer,
    onError = cargo_theme_dark_onError,
    onErrorContainer = cargo_theme_dark_onErrorContainer,
    background = cargo_theme_dark_background,
    onBackground = cargo_theme_dark_onBackground,
    surface = cargo_theme_dark_surface,
    onSurface = cargo_theme_dark_onSurface,
    surfaceVariant = cargo_theme_dark_surfaceVariant,
    onSurfaceVariant = cargo_theme_dark_onSurfaceVariant,
    outline = cargo_theme_dark_outline,
    outlineVariant = cargo_theme_dark_outlineVariant,
)