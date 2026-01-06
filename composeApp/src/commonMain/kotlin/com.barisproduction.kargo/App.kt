package com.barisproduction.kargo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.barisproduction.kargo.navigation.NavigationGraph
import com.barisproduction.kargo.navigation.Screen.Splash
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    MaterialTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ) {
            NavigationGraph(
                navController = navController,
                startDestination = Splash
            )
        }
    }
}