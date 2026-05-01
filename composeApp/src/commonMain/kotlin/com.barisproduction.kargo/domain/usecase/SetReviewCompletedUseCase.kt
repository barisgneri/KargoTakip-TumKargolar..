package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.ReviewPromptRepository

class SetReviewCompletedUseCase(
    private val reviewPromptRepository: ReviewPromptRepository
) {
    suspend operator fun invoke(value: Boolean) {
        reviewPromptRepository.setReviewCompleted(value)
    }
}
