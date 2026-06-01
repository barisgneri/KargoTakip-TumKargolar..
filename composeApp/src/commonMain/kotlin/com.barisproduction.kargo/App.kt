package com.barisproduction.kargo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.barisproduction.kargo.domain.repository.AppConfigRepository
import com.barisproduction.kargo.navigation.NavigationGraph
import com.barisproduction.kargo.navigation.Screen.Splash
import com.barisproduction.kargo.ui.theme.KargoTheme
import org.koin.compose.koinInject

@Composable
fun App() {
    val appConfigRepository: AppConfigRepository = koinInject()
    
    val isDarkMode by appConfigRepository.isDarkMode.collectAsStateWithLifecycle()
    val currentLanguage by appConfigRepository.currentLanguage.collectAsStateWithLifecycle()
    
    val darkTheme = isDarkMode ?: isSystemInDarkTheme()

    LaunchedEffect(currentLanguage) {
        currentLanguage?.let { langCode ->
            changeLanguage(langCode)
        }
    }

    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }

    val navController = rememberNavController()
    KargoTheme(darkTheme = darkTheme) {
        Scaffold {
            NavigationGraph(
                navController = navController,
                startDestination = Splash
            )
        }
    }
}
