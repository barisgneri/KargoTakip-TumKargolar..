package com.barisproduction.kargo.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.*

@Composable
fun getInterFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.inter_regular, FontWeight.Normal),
        Font(Res.font.inter_medium, FontWeight.Medium),
        Font(Res.font.inter_semibold, FontWeight.SemiBold),
        Font(Res.font.inter_bold, FontWeight.Bold)
    )
}

@Composable
fun getAppTypography(): Typography {
    val interFamily = getInterFontFamily()

    return Typography(
        // Örn: "Aktif Gönderiler" başlığı
        headlineSmall = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp
        ),

        // Örn: "Kargolarım" App Bar başlığı
        titleLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),

        // Örn: Kart içindeki "Yeni Telefon" yazısı
        titleMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),

        // standart metinler
        bodyLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),

        // Örn: Alt açıklamalar
        bodyMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),

        // Örn: Input etiketleri, "Takip Numarası"
        labelLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),

        // Örn: Tarih detayları
        labelMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),

        // çok küçük etiketler
        labelSmall = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )
}