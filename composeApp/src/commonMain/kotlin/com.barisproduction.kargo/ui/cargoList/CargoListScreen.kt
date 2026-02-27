package com.barisproduction.kargo.ui.cargoList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.barisproduction.kargo.common.extensions.collectWithLifecycle
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.ui.splash.SplashScreenPreviewProvider
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

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

            is UiEffect.NavigateToTracking -> navActions.navigateToTracking(it.parcelName, it.trackingNumber)
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
            cargoList = uiState.list,
            onAction = onAction
        )

    }
}

@Composable
fun CargoList(
    modifier: Modifier = Modifier,
    cargoList: List<CargoModel>,
    onAction: (UiAction) -> Unit
) {
    if (cargoList.isEmpty()) {
        EmptyCargoView(modifier = modifier)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cargoList, key = { it.trackNo }) { cargo ->
                val scope = rememberCoroutineScope()
                val dismissState = rememberSwipeToDismissBoxState(
                    initialValue = SwipeToDismissBoxValue.Settled,
                    positionalThreshold = { it * 0.25f }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                            Color.Red.copy(alpha = 0.8f)
                        } else Color.Transparent

                        Box(
                            Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // DÜZENLE
                                IconButton(
                                    onClick = {
                                        onAction(UiAction.EditCargo(cargo.trackNo))
                                        scope.launch { dismissState.reset() }
                                    },
                                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = "Düzenle", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                }

                                // SİL
                                IconButton(
                                    onClick = {
                                        onAction(UiAction.DeleteCargo(trackNo = cargo.trackNo))
                                        scope.launch { dismissState.reset() }
                                    },
                                    modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer, CircleShape)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Sil", tint = MaterialTheme.colorScheme.onErrorContainer)
                                }
                            }
                        }
                    }
                ){
                    CargoItem(cargo = cargo, onAction = onAction)
                }

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
fun CargoItem(cargo: CargoModel, onAction: (UiAction) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onAction(UiAction.NavigateToTracking(cargo.parcelName, cargo.trackNo)) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = cargo.logo,
                    contentDescription = cargo.parcelName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                val header = if (cargo.cargoName.isNullOrBlank()){
                    cargo.parcelName
                }else{
                    "${cargo.cargoName} - ${cargo.parcelName}"
                }
                Text(
                    text = header,
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

@Preview(showBackground = true)
@Composable
fun CargoListPreview(@PreviewParameter(SplashScreenPreviewProvider::class) uiState: UiState) {
    KargoTheme {
        CargoListScreen(
            uiState = uiState,
            uiEffect = emptyFlow(),
            onAction = {},
            navActions = CargoListNavActions.default
        )
    }

}