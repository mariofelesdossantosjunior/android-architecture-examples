package br.com.mobiplus.gitclient.data.issues

import br.com.mobiplus.gitclient.data.fake.FakeIssuesModel
import br.com.mobiplus.gitclient.data.issues.model.IssuesResponseModel
import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.FullResultWrapper
import br.com.mobiplus.gitclient.domain.model.GithubError
import br.com.mobiplus.gitclient.domain.model.IssuesModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class IssuesRepositoryImplTest {

    @MockK
    private lateinit var issuesAPIDataSource: IssuesAPIDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getIssuesList() {
        val resultMock = FakeIssuesModel.mockResult()
        val resultTransformed = FakeIssuesModel.mockResultTransformed()

        lateinit var result: FullResultWrapper<List<IssuesModel>, BaseErrorData<GithubError>>

        coEvery {
            issuesAPIDataSource.getIssuesList(any(), any())
        } returns resultMock

        val issuesRepositoryImpl =
            spyk(
                IssuesRepositoryImpl(
                    issuesAPIDataSource
                ), recordPrivateCalls = true
            )

        runBlocking {
            result = issuesRepositoryImpl.getIssuesList(
                owner = "owner",
                gitRepoName = "gitRepoName"
            )
        }

        coVerify {
            issuesAPIDataSource.getIssuesList("owner", "gitRepoName")
        }

        coVerify {
            issuesRepositoryImpl.getIssuesList(any(), any())
            issuesRepositoryImpl["handleGetIssuesSuccess"]()
        }

        coVerifySequence {
            issuesRepositoryImpl.getIssuesList(any(), any())
            issuesRepositoryImpl["handleGetIssuesSuccess"]()
        }

        confirmVerified(issuesRepositoryImpl)

        confirmVerified(issuesAPIDataSource)

        assertEquals(result.success, resultTransformed.success)
    }
}