package domain.usecase

import com.barisproduction.kargo.domain.usecase.SetReviewCompletedUseCase
import domain.FakeReviewPromptRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class SetReviewCompletedUseCaseTest {

    private lateinit var fakeRepository: FakeReviewPromptRepository
    private lateinit var setReviewCompletedUseCase: SetReviewCompletedUseCase

    @BeforeTest
    fun setup() {
        fakeRepository = FakeReviewPromptRepository()
        setReviewCompletedUseCase = SetReviewCompletedUseCase(fakeRepository)
    }

    @Test
    fun `invoke review durumunu dogru sekilde guncellemeli`() = runTest {
        // Arrange
        val newValue = true

        // Act
        setReviewCompletedUseCase(newValue)

        // Assert
        assertTrue(fakeRepository.isReviewCompleted())
    }
}
