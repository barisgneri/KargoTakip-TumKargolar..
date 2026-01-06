package com.barisproduction.kargo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.add_new_cargo
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Composable
fun AnimAddCargoFAB(
    onClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(1000)
        isExpanded = true
        delay(4000)
        isExpanded = false
    }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 360f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "FabRotation"
    )

    ExtendedFloatingActionButton(
        onClick = onClick,
        expanded = isExpanded,
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Ekle",
                modifier = Modifier.rotate(rotationAngle)
            )
        },
        text = {
            Text(text = stringResource(Res.string.add_new_cargo))
        },
        shape = RoundedCornerShape(16.dp),
    )
}