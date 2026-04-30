package com.barisproduction.kargo.ui.cargoList.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.barisproduction.kargo.ui.cargoList.CargoListContract.UiAction
import com.barisproduction.kargo.ui.components.CargoBaseDialog
import com.barisproduction.kargo.ui.theme.spacing
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.review_app_confirm
import kargotakiptumkargolar.composeapp.generated.resources.review_app_description
import kargotakiptumkargolar.composeapp.generated.resources.review_app_later
import kargotakiptumkargolar.composeapp.generated.resources.review_app_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun RatingDialog(
    onAction: (UiAction) -> Unit,
    selectedRating: Int
){
    CargoBaseDialog(
        onDismissRequest = { onAction(UiAction.OnReviewDismiss) },
        title = stringResource(Res.string.review_app_title),
        description = stringResource(Res.string.review_app_description),
        confirmButtonText = stringResource(Res.string.review_app_confirm),
        onConfirmClick = { onAction(UiAction.OnReviewConfirm) },
        dismissButtonText = stringResource(Res.string.review_app_later),
        onDismissClick = { onAction(UiAction.OnReviewDismiss) },
        content = {
            RatingSelector(
                selectedRating = selectedRating,
                onRatingSelected = { onAction(UiAction.OnReviewRatingChanged(it)) }
            )
        }
    )
}

@Composable
private fun RatingSelector(
    selectedRating: Int,
    onRatingSelected: (Int) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
        (1..5).forEach { rating ->
            IconButton(onClick = { onRatingSelected(rating) }) {
                Icon(
                    imageVector = if (rating <= selectedRating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "Rating $rating",
                    tint = if (rating <= selectedRating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}