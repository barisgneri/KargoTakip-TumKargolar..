package com.barisproduction.kargo.ui.addCargo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barisproduction.kargo.common.collectWithLifecycle
import com.barisproduction.kargo.domain.model.ParcelModel
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiAction
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiEffect
import com.barisproduction.kargo.ui.addCargo.AddCargoContract.UiState
import com.barisproduction.kargo.ui.addCargo.components.AddCargoTopBar
import com.barisproduction.kargo.ui.addCargo.components.KargoTextField
import com.barisproduction.kargo.ui.components.ActionOutlineButton
import com.barisproduction.kargo.ui.theme.Dimens
import kotlinx.coroutines.flow.Flow

@Composable
fun AddCargoScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navActions: AddCargoNavActions
) {
    uiEffect.collectWithLifecycle {
        when (it) {
            is UiEffect.NavigateBack -> navActions.onBack()
            is UiEffect.ShowToast -> {
            }
        }
    }

    Scaffold(
        topBar = {
            AddCargoTopBar(onBack = { onAction(UiAction.OnBackClick) }, onSave = { onAction(UiAction.OnSaveClick) })
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Dimens.paddingMedium),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Takip numaranı girerek başla",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.height(Dimens.paddingLarge))

                Text(
                    text = "Takip Numarası",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimens.paddingSmall))
                KargoTextField(
                    value = uiState.trackingNumber,
                    onValueChange = { onAction(UiAction.OnTrackingNumberChange(it)) },
                    placeholder = "1Z9999W 99999999999",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(Dimens.paddingMedium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionOutlineButton(
                        text = "Barkod Tara",
                        icon = Icons.Outlined.QrCodeScanner,
                        isEnabled = false,
                        onClick = { onAction(UiAction.OnScanBarcode) },
                        modifier = Modifier.weight(1f)
                    )
                    ActionOutlineButton(
                        text = "Panodan Yapıştır",
                        icon = Icons.Outlined.ContentPaste,
                        onClick = {
                                onAction(UiAction.OnPasteClipboard)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.paddingLarge))

                // Always show carrier selection (either selected or query)
                CarrierSelectionTrigger(
                    carrier = uiState.detectedCarrier,
                    onClick = { onAction(UiAction.OnCarrierSelectClick) }
                )
                Spacer(modifier = Modifier.height(Dimens.paddingLarge))

                Text(
                    text = "Kargo Adı (İsteğe Bağlı)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimens.paddingSmall))
                KargoTextField(
                    value = uiState.cargoName,
                    onValueChange = { onAction(UiAction.OnCargoNameChange(it)) },
                    placeholder = "Örn: Sipariş: Ayakkabı",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }

            Button(
                onClick = { onAction(UiAction.OnSaveClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Kargoyu Ekle",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }

    if (uiState.isCarrierSelectionVisible) {
        CarrierSelectionSheet(
            onDismissRequest = { onAction(UiAction.OnCarrierSelectDismiss) },
            onCarrierSelected = { onAction(UiAction.OnCarrierSelected(it)) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrierSelectionSheet(
    onDismissRequest: () -> Unit,
    onCarrierSelected: (ParcelModel) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingMedium)
        ) {
            Text(
                "Kargo Firması Seçin",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = Dimens.paddingMedium)
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxWidth().height(400.dp), // Limit height
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ParcelModel.entries) { carrier ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCarrierSelected(carrier) }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(MaterialTheme.colorScheme.tertiaryContainer, shape = RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                carrier.parcelName.take(1),
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = carrier.parcelName,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CarrierSelectionTrigger(
    carrier: ParcelModel?,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp) // Touch target
    ) {
        if (carrier != null) {
            DetectedCarrierView(carrier = carrier)
        } else {
             Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), 
                        shape = RoundedCornerShape(8.dp)
                    ), 
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Add, // Or search icon
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    "Kargo Firması", 
                    style = MaterialTheme.typography.labelSmall, 
                    color = MaterialTheme.colorScheme.onSurfaceVariant, 
                    fontSize = 12.sp
                )
                Text(
                    "Firma Seçin", 
                    style = MaterialTheme.typography.bodyMedium, 
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), 
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Select",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DetectedCarrierView(carrier: ParcelModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer, 
                    shape = RoundedCornerShape(8.dp)
                ), 
            contentAlignment = Alignment.Center
        ) {
            Text(
                carrier.parcelName.take(1), 
                color = MaterialTheme.colorScheme.onTertiaryContainer, 
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                "Kargo Firması", 
                style = MaterialTheme.typography.labelSmall, 
                color = MaterialTheme.colorScheme.onSurfaceVariant, 
                fontSize = 12.sp
            )
            Text(
                "${carrier.parcelName} Algılandı", 
                style = MaterialTheme.typography.bodyMedium, 
                color = MaterialTheme.colorScheme.onSurface, 
                fontWeight = FontWeight.Bold
            )
        }
    }
}
