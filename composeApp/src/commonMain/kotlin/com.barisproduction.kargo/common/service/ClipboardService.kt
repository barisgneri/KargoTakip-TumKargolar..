package com.barisproduction.kargo.common.service

interface ClipboardService {
    suspend fun getText(): String?
    suspend fun setText(text: String)
}