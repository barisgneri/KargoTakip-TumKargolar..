package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.common.service.ClipboardService

class GetClipboardTextUseCase(
    private val clipboardService: ClipboardService
) {
    suspend operator fun invoke(): String? {
        return clipboardService.getText()
    }
}
