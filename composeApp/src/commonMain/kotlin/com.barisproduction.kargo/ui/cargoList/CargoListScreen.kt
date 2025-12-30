package com.barisproduction.kargo.ui.cargoList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barisproduction.kargo.common.collectWithLifecycle

import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiAction
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiEffect
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiState
import com.barisproduction.kargo.ui.components.AnimAddCargoFAB
import com.barisproduction.kargo.ui.components.LoadingBar
import com.barisproduction.kargo.ui.theme.KargoTheme
import kotlinx.coroutines.flow.Flow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.barisproduction.kargo.domain.model.CargoModel

import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CargoListScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navActions: CargoListNavActions
) {
    uiEffect.collectWithLifecycle {
        when (it) {
            is UiEffect.ShowError -> {}

            is UiEffect.NavigateToAddNewCargo -> navActions.addNewCargoNavigation()
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        else -> CargoListContent(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
            onAction = onAction,
        )
    }

}

@Composable
fun CargoListContent(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
) {
    Scaffold(modifier = modifier, floatingActionButton = {
        AnimAddCargoFAB(onClick = {
            onAction(UiAction.AddNewCargo)
        })
    }) { innerPadding ->
        CargoList(
            modifier = Modifier.padding(innerPadding),
            cargoList = uiState.list
        )

    }
}

@Composable
fun CargoList(
    modifier: Modifier = Modifier,
    cargoList: List<CargoModel>
) {
    if (cargoList.isEmpty()) {
        EmptyCargoView(modifier = modifier)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cargoList) { cargo ->
                CargoItem(cargo = cargo)
            }
        }
    }
}

@Composable
fun EmptyCargoView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Hemen kargonu sorgula!",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Listede hiç kargo yok.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun CargoItem(cargo: CargoModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo placeholder
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = cargo.parcel.parcelName.take(1),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cargo.cargoName,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Takip No: ${cargo.trackNo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Eklenme: ${cargo.addDate}", // TODO: Format Date
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
fun CargoListPreview() {
    KargoTheme {

    }

    //  CargoListScreen(UiState(),flowOf(), {}, CargoListNavActions.default)
}