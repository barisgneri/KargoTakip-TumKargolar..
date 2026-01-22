package com.barisproduction.kargo.service

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.barisproduction.kargo.common.service.ClipboardService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AndroidClipboardService(
    private val context: Context
) : ClipboardService {

    private val clipboardManager: ClipboardManager by lazy {
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    override suspend fun getText(): String? = withContext(Dispatchers.IO) {
        if (!clipboardManager.hasPrimaryClip()) return@withContext null

        val item = clipboardManager.primaryClip?.getItemAt(0)
        item?.text?.toString()
    }

    override suspend fun setText(text: String) = withContext(Dispatchers.Main) {
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboardManager.setPrimaryClip(clip)
    }
}