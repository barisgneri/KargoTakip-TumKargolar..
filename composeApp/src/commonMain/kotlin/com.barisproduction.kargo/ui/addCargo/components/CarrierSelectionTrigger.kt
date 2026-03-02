package com.barisproduction.kargo.ui.addCargo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.ui.theme.Dimens
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun CarrierSelectionTrigger(
    carrier: Parcels?,
    isError: Boolean = false,
    onClick: () -> Unit
) {
    Text(
        text = stringResource(Res.string.cargo_company),
        style = MaterialTheme.typography.labelLarge
    )
    Spacer(modifier = Modifier.height(Dimens.paddingSmall))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.buttonHeight)
            .clickable(onClick = onClick)
            .border(
                width = if (isError) 1.dp else 0.dp,
                color = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(vertical = 4.dp)
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
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(Res.string.select_cargo_company),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )
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
private fun DetectedCarrierView(carrier: Parcels) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = carrier.logo,
                contentDescription = carrier.parcelName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize().clip(RoundedCornerShape(8.dp)),
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                stringResource(Res.string.cargo_company),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )
            Text(
                "${carrier.parcelName} Seçildi",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
