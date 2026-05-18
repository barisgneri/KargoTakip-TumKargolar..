package domain

import com.barisproduction.kargo.data.repository.ReviewPromptRepositoryImpl
import data.preferences.FakeReviewPreferenceStore
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ReviewPromptRepositoryImplTest {

    private lateinit var fakeStore: FakeReviewPreferenceStore
    private lateinit var repository: ReviewPromptRepositoryImpl

    @BeforeTest
    fun setup() {
        fakeStore = FakeReviewPreferenceStore()
        repository = ReviewPromptRepositoryImpl(fakeStore)
    }

    @Test
    fun `isReviewCompleted varsayilan olarak false donmeli`() = runTest {
        val result = repository.isReviewCompleted()
        assertEquals(false, result)
    }

    @Test
    fun `setReviewCompleted degeri dogru kaydetmeli`() = runTest {
        repository.setReviewCompleted(true)
        val result = repository.isReviewCompleted()
        assertEquals(true, result)
    }
}
