package com.barisproduction.kargo.common.service

import android.content.Context
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.tasks.await

class AndroidBarcodeScannerService(
    private val context: Context
) : BarcodeScannerService {

    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .enableAutoZoom()
        .build()

    private val scanner = GmsBarcodeScanning.getClient(context, options)

    override suspend fun startScan(): String? {
        return try {
            val result = scanner.startScan().await()
            result.rawValue
        } catch (e: Exception) {
            null
        }
    }
}
