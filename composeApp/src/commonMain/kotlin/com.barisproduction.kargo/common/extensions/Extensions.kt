package com.barisproduction.kargo.common.extensions

import com.barisproduction.kargo.common.AppError

fun AppError.toUserMessage(): String {
    return when (this) {
        is AppError.NetworkConnection -> "Lütfen internet bağlantınızı kontrol edin."
        is AppError.ServerUnavailable -> "Sunucu şu an hizmet veremiyor."
        is AppError.NotFound -> "İstenilen veri bulunamadı."
        is AppError.Unauthorized -> "Oturum süresi doldu."
        is AppError.General -> this.message
        is AppError.WebViewError -> "WebView hatası: $errorCode"
        else -> "Beklenmedik bir hata oluştu."
    }
}