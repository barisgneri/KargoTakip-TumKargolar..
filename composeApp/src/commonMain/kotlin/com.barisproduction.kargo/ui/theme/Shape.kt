package com.barisproduction.kargo.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    //Küçük çip veya tag'ler için
    extraSmall = RoundedCornerShape(4.dp),

    // İkon zeminleri veya küçük resimler için
    small = RoundedCornerShape(8.dp),

    // Ana kartlar, text field'lar ve büyük butonlar için
    medium = RoundedCornerShape(12.dp),

    // Dialoglar veya bottom sheet'ler için
    large = RoundedCornerShape(16.dp),

    // Ekstra büyük oval yapılar için
    extraLarge = RoundedCornerShape(24.dp)
)