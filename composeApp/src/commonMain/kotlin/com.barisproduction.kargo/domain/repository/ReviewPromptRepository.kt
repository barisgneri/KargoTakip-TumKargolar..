package com.barisproduction.kargo.domain.repository

interface ReviewPromptRepository {
    suspend fun isReviewCompleted(): Boolean
    suspend fun setReviewCompleted(value: Boolean)
}
