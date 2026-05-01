package com.barisproduction.kargo.data.repository

import com.barisproduction.kargo.data.preferences.ReviewPreferenceStore
import com.barisproduction.kargo.domain.repository.ReviewPromptRepository

class ReviewPromptRepositoryImpl(
    private val reviewPreferenceStore: ReviewPreferenceStore
) : ReviewPromptRepository {

    override suspend fun isReviewCompleted(): Boolean {
        return reviewPreferenceStore.getBoolean(KEY_REVIEW_COMPLETED, false)
    }

    override suspend fun setReviewCompleted(value: Boolean) {
        reviewPreferenceStore.putBoolean(KEY_REVIEW_COMPLETED, value)
    }

    private companion object {
        const val KEY_REVIEW_COMPLETED = "review_completed"
    }
}
