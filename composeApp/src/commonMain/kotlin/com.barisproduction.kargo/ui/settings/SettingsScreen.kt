package com.barisproduction.kargo.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.barisproduction.kargo.common.extensions.collectWithLifecycle
import com.barisproduction.kargo.ui.components.SelectionBottomSheet
import com.barisproduction.kargo.ui.settings.SettingsContract.PickerType
import com.barisproduction.kargo.ui.settings.SettingsContract.UiAction
import com.barisproduction.kargo.ui.settings.SettingsContract.UiEffect
import com.barisproduction.kargo.ui.settings.SettingsContract.UiState
import com.barisproduction.kargo.ui.settings.components.SettingsClickableCard
import com.barisproduction.kargo.ui.settings.components.SettingsSection
import com.barisproduction.kargo.ui.theme.KargoTheme
import com.barisproduction.kargo.ui.theme.spacing
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.back
import kargotakiptumkargolar.composeapp.generated.resources.change_country
import kargotakiptumkargolar.composeapp.generated.resources.change_country_info
import kargotakiptumkargolar.composeapp.generated.resources.change_language
import kargotakiptumkargolar.composeapp.generated.resources.change_language_info
import kargotakiptumkargolar.composeapp.generated.resources.dark_mode
import kargotakiptumkargolar.composeapp.generated.resources.select_country
import kargotakiptumkargolar.composeapp.generated.resources.select_language
import kargotakiptumkargolar.composeapp.generated.resources.settings
import kargotakiptumkargolar.composeapp.generated.resources.theme_setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    actions: SettingsNavActions
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            UiEffect.NavigateBack -> actions.onBack()
        }
    }

    uiState.activePicker?.let { picker ->
        when (picker) {
            PickerType.COUNTRY -> {
                SelectionBottomSheet(
                    title = stringResource(picker.titleRes),
                    items = uiState.countries,
                    itemLabel = { it.displayName },
                    onDismiss = { onAction(UiAction.OnDismissPicker) },
                    onItemSelected = { onAction(UiAction.OnCountrySelect(it)) }
                )
            }

            PickerType.LANGUAGE -> {
                SelectionBottomSheet(
                    title = stringResource(picker.titleRes),
                    items = uiState.languages,
                    itemLabel = { it.name },
                    onDismiss = { onAction(UiAction.OnDismissPicker) },
                    onItemSelected = { onAction(UiAction.OnLanguageSelect(it)) }
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(UiAction.OnBackClick) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(spacing.medium),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            // Theme Setting
            SettingsSection(title = stringResource(Res.string.theme_setting)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing.small),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        stringResource(Res.string.dark_mode),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = uiState.isDarkMode,
                        onCheckedChange = { onAction(UiAction.OnDarkModeChange(it)) }
                    )
                }
            }

            // Country Setting Card
            SettingsClickableCard(
                icon = Icons.Default.Language,
                title = stringResource(Res.string.change_country),
                description = stringResource(Res.string.change_country_info),
                selectedValue = uiState.selectedCountry,
                onClick = { onAction(UiAction.OnCountryClick) }
            )

            // Language Setting Card
            SettingsClickableCard(
                icon = Icons.Default.Translate,
                title = stringResource(Res.string.change_language),
                description = stringResource(Res.string.change_language_info),
                selectedValue = uiState.selectedLanguage,
                onClick = { onAction(UiAction.OnLanguageClick) }
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    KargoTheme {
        SettingsScreen(
            uiState = UiState(
                isDarkMode = false,
                selectedCountry = "Türkiye",
                selectedLanguage = "Türkçe"
            ),
            uiEffect = emptyFlow(),
            onAction = {},
            actions = SettingsNavActions(onBack = {})
        )
    }
}
