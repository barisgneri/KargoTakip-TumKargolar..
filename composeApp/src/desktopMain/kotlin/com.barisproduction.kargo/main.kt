package com.barisproduction.kargo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.barisproduction.kargo.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "KargoTakipTumKargolar",
    ) {
        App()
    }
}