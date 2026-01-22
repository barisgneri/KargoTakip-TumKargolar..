package com.barisproduction.kargo.service

import com.barisproduction.kargo.common.service.ClipboardService
import platform.UIKit.UIPasteboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IosClipboardService : ClipboardService {

    override suspend fun getText(): String? = withContext(Dispatchers.Main) {
        UIPasteboard.generalPasteboard.string
    }

    override suspend fun setText(text: String) = withContext(Dispatchers.Main) {
        UIPasteboard.generalPasteboard.string = text
    }
}