package domain.usecase

import com.barisproduction.kargo.domain.usecase.GetReviewCompletedUseCase
import domain.FakeReviewPromptRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetReviewCompletedUseCaseTest {

    private lateinit var fakeRepository: FakeReviewPromptRepository
    private lateinit var getReviewCompletedUseCase: GetReviewCompletedUseCase

    @BeforeTest
    fun setup() {
        fakeRepository = FakeReviewPromptRepository()
        getReviewCompletedUseCase = GetReviewCompletedUseCase(fakeRepository)
    }

    @Test
    fun `invoke varsayilan olarak false donmeli`() = runTest {
        // Act
        val result = getReviewCompletedUseCase()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `invoke repo guncellendiginde true donmeli`() = runTest {
        // Arrange
        fakeRepository.setReviewCompleted(true)

        // Act
        val result = getReviewCompletedUseCase()

        // Assert
        assertTrue(result)
    }
}
