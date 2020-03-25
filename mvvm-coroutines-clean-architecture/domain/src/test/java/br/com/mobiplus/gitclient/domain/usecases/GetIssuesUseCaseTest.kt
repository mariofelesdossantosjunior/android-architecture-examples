package br.com.mobiplus.gitclient.domain.usecases

import br.com.mobiplus.gitclient.domain.*
import br.com.mobiplus.gitclient.domain.base.resultwrapper.FullResultWrapper
import br.com.mobiplus.gitclient.domain.model.IssuesModel
import br.com.mobiplus.gitclient.domain.model.PullRequestModel
import br.com.mobiplus.gitclient.domain.model.UserModel
import br.com.mobiplus.gitclient.domain.repository.IssuesRepository
import br.com.mobiplus.gitclient.domain.repository.PullRequestRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetIssuesUseCaseTest {

    @MockK
    private lateinit var issuesRepository: IssuesRepository

    private lateinit var params: GetIssuesUseCase.Params

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        params = FakeGetIssuesUseCase.Params.mock()
    }

    /**
     * This test was added just for reference purposes
     */
    @Test
    fun `SHOULD return the correct success object`() = runBlocking {
        val resultMock = FakeIssuesModel.mockResult()

        coEvery {
            issuesRepository.getIssuesList(any(), any())
        } returns resultMock

        val result = GetIssuesUseCase(issuesRepository).runAsync(params)

        assertEquals(resultMock.success, result.success)
    }

    @Test
    fun `SHOULD call correct dependency function WHEN run`() = runBlocking {
        val resultMock = FakeIssuesModel.mockResult()

        coEvery {
            issuesRepository.getIssuesList(any(), any())
        } returns resultMock

        GetIssuesUseCase(issuesRepository).runAsync(params)

        coVerify {
            issuesRepository.getIssuesList(params.owner, params.gitRepoName)
        }

        confirmVerified(issuesRepository)
    }
}