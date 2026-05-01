package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.ReviewPromptRepository

class GetReviewCompletedUseCase(
    private val reviewPromptRepository: ReviewPromptRepository
) {
    suspend operator fun invoke(): Boolean {
        return reviewPromptRepository.isReviewCompleted()
    }
}
