package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.common.service.BarcodeScannerService

class ScanBarcodeUseCase(
    private val barcodeScannerService: BarcodeScannerService
) {
    suspend operator fun invoke(): String? {
        return barcodeScannerService.startScan()
    }
}
