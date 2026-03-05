package com.barisproduction.kargo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
expect fun KargoWebView(
    url: String,
    modifier: Modifier,
    onLoadingStateChanged: (Boolean) -> Unit,
    onError: (String) -> Unit,
    js: String
)