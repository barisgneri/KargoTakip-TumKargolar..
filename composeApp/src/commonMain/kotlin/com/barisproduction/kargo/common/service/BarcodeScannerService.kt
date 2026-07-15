package com.barisproduction.kargo.common.service

interface BarcodeScannerService {
    suspend fun startScan(): String?
}
