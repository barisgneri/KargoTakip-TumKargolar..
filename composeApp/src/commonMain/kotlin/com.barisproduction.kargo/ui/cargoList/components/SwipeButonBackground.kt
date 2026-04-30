package com.barisproduction.kargo.ui.cargoList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiAction
import kotlinx.coroutines.launch

@Composable
fun SwipeButtonBackground(dismissState: SwipeToDismissBoxState, cargo: CargoModel, onAction: (UiAction) -> Unit) {
    val scope = rememberCoroutineScope()
    val color =
        if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else Color.Transparent

    Box(
        Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .padding(horizontal = 20.dp)
            .clickable {
                scope.launch { dismissState.reset() }
            },
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // DÜZENLE
            IconButton(
                onClick = {
                    onAction(
                        UiAction.EditCargo(
                            cargo.parcelName,
                            cargo.trackNo,
                            cargo.cargoName ?: ""
                        )
                    )
                    scope.launch { dismissState.reset() }
                },
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.primaryContainer,
                    CircleShape
                )
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Düzenle",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // SİL
            IconButton(
                onClick = {
                    onAction(UiAction.RequestDelete(trackNo = cargo.trackNo))
                    scope.launch { dismissState.reset() }
                },
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.errorContainer,
                    CircleShape
                )
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Sil",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}