package br.com.mobiplus.gitclient.data.repository

import br.com.mobiplus.gitclient.data.fake.FakePullRequestResponseModel
import br.com.mobiplus.gitclient.data.fake.FakeUserResponseModel
import br.com.mobiplus.gitclient.data.pullRequest.PullRequestAPIDataSource
import br.com.mobiplus.gitclient.data.pullRequest.PullRequestRepositoryImpl
import br.com.mobiplus.gitclient.data.pullRequest.model.PullRequestResponseModel
import br.com.mobiplus.gitclient.data.pullRequest.model.UserResponseModel
import br.com.mobiplus.gitclient.domain.base.resultwrapper.FullResultWrapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class PullRequestRepositoryImplTest {

    @MockK
    private lateinit var pullRequestAPIDataSource: PullRequestAPIDataSource

    private lateinit var userModel: UserResponseModel
    private lateinit var pullRequestModel: PullRequestResponseModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        userModel = FakeUserResponseModel.loadUserResponse()
        pullRequestModel = FakePullRequestResponseModel.loadPullRequestResponse(userModel)
    }

    @Test
    fun listPullRequests() {

        coEvery {
            pullRequestAPIDataSource.getPullRequestList(any(), any())
        } returns FullResultWrapper(
            success = listOf(pullRequestModel)
        )

        runBlocking {
            PullRequestRepositoryImpl(pullRequestAPIDataSource).getPullRequestList(
                owner = "",
                gitRepoName = ""
            )
        }


        coVerify {
            pullRequestAPIDataSource.getPullRequestList(any(), any())
        }

        confirmVerified(pullRequestAPIDataSource)
    }

    @Test
    fun `SHOULD call functions in the correct order`() {

        coEvery {
            pullRequestAPIDataSource.getPullRequestList(any(), any())
        } returns FullResultWrapper(
            success = listOf(pullRequestModel)
        )

        val pullRequestRepositoryImpl =
            spyk(
                PullRequestRepositoryImpl(
                    pullRequestAPIDataSource
                ), recordPrivateCalls = true
            )

        runBlocking {
            pullRequestRepositoryImpl.getPullRequestList("", "")
        }

        coVerify {
            pullRequestRepositoryImpl.getPullRequestList(any(), any())
            pullRequestRepositoryImpl["handleGetPullRequestsSuccess"]()
            //pullRequestRepositoryImpl["handleListPullRequestsError"]()
        }

        coVerifySequence {
            pullRequestRepositoryImpl.getPullRequestList(any(), any())
            pullRequestRepositoryImpl["handleGetPullRequestsSuccess"]()
            //pullRequestRepositoryImpl["handleListPullRequestsError"]()
        }

        confirmVerified(
            pullRequestRepositoryImpl
        )
    }

    @After
    fun tearDown() {
    }
}