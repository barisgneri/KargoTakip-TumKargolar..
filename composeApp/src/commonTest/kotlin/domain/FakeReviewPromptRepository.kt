package domain

import com.barisproduction.kargo.domain.repository.ReviewPromptRepository

class FakeReviewPromptRepository : ReviewPromptRepository {
    private var isCompleted = false

    override suspend fun isReviewCompleted(): Boolean {
        return isCompleted
    }

    override suspend fun setReviewCompleted(value: Boolean) {
        isCompleted = value
    }
}
