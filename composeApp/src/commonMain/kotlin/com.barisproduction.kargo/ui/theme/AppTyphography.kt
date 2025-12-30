package com.barisproduction.kargo.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography

@Composable
fun getAppTypography(): Typography {
    val montserratFamily = FontFamily(
       // Font(Res.font.montserrat_regular),
      // Font(Res.font.montserrat_bold)
    )

    return Typography(
        titleLarge = TextStyle(
            fontFamily = montserratFamily,
            fontSize = 22.sp
        ),
    )
}