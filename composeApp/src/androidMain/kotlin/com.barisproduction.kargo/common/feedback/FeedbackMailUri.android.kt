package com.barisproduction.kargo.common.feedback

import android.net.Uri
import android.os.Build

actual fun buildFeedbackMailUri(): String {
    return "mailto:barisgnrweb@gmail.com" +
        "?subject=" + Uri.encode("Uygulama Geri Bildirimi") +
        "&body=" + Uri.encode(
            "Merhaba,\n\nYaşadığım sorun:\n\n" +
                "Cihaz: ${Build.MODEL}\n" +
                "Android: ${Build.VERSION.RELEASE}\n"
        )
}
