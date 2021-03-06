package br.com.mobiplus.gitclient.presentation.ui.activity

import android.content.Intent
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import br.com.mobiplus.gitclient.R
import br.com.mobiplus.gitclient.domain.FakePullRequestModel
import br.com.mobiplus.gitclient.domain.FakeResultWrapper
import br.com.mobiplus.gitclient.domain.FakeUserModel
import br.com.mobiplus.gitclient.domain.usecases.GetPullRequestUseCase
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.details.PullRequestActivity
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.details.PullRequestViewModel
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4ClassRunner::class)
class PullRequestActivityTest {

    @get:Rule
    val rule = ActivityTestRule(PullRequestActivity::class.java, false, false)

    @MockK
    private lateinit var getPullRequestUseCase: GetPullRequestUseCase

    @Before
    fun before() {

        MockKAnnotations.init(this)

        loadKoinModules(module {
            viewModel(override = true) {
                PullRequestViewModel(
                    getPullRequestUseCase
                )
            }
        })
    }

    @Test
    fun openActivity() {
        coEvery {
            getPullRequestUseCase.runAsync(any())
        } returns FakeResultWrapper.mockSuccess(
            FakePullRequestModel.mock(
                FakeUserModel.mock()
            )
        )

        val intent = Intent()
        intent.putExtra("owner", "TEST_OWNER")
        intent.putExtra("gitRepoName", "TEST_REPO")
        intent.putExtra("pullRequestNumber", 1000L)

        rule.launchActivity(intent)

        sleep(300)

        assertDisplayed(R.id.textAuthorName, "luis.fernandez")
    }
}