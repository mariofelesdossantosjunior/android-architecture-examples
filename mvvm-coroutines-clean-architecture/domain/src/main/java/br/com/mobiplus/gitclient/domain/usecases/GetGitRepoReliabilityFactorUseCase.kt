package br.com.mobiplus.gitclient.domain.usecases

import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.ResultWrapper
import br.com.mobiplus.gitclient.domain.base.usecase.BaseUseCase
import br.com.mobiplus.gitclient.domain.model.FeatureFlagModel
import br.com.mobiplus.gitclient.domain.model.GitRepoStatsModel
import br.com.mobiplus.gitclient.domain.model.GithubError
import br.com.mobiplus.gitclient.domain.repository.GitRepoRepository

class GetGitRepoReliabilityFactorUseCase(
    private val gitRepoRepository: GitRepoRepository
) : BaseUseCase<ResultWrapper<FeatureFlagModel<Double>, BaseErrorData<GithubError>>, GetGitRepoReliabilityFactorUseCase.Params>() {

    override fun runSync(params: Params): ResultWrapper<FeatureFlagModel<Double>, BaseErrorData<GithubError>> {
        val flag = gitRepoRepository.getGitRepoReliabilityMultiplier()

        return if (flag.enabled) {
            val result = gitRepoRepository.getGitRepoStats(params.owner, params.gitRepoName)

            result.transformSuccess(
                this.transformSuccess(flag)
            )
        } else {
            ResultWrapper(
                success = FeatureFlagModel(
                    enabled = false,
                    data = 0.0
                )
            )
        }
    }

    private fun transformSuccess(flag: FeatureFlagModel<Int>): (GitRepoStatsModel) -> FeatureFlagModel<Double> {
        return { gitRepoStatsModel ->
            FeatureFlagModel(
                enabled = flag.enabled,
                data = calculateReliabilityFactor(
                    engagementMultiplier = flag.data ?: 1,
                    closedIssues = gitRepoStatsModel.closedIssues,
                    openedIssues = gitRepoStatsModel.openedIssues,
                    mergedPullRequests = gitRepoStatsModel.mergedPullRequests,
                    proposedPullRequests = gitRepoStatsModel.proposedPullRequests
                )
            )
        }
    }

    private fun calculateReliabilityFactor(
        engagementMultiplier: Int,
        closedIssues: Int,
        openedIssues: Int,
        mergedPullRequests: Int,
        proposedPullRequests: Int
    ): Double {
        val issuesPoints = calculateIssuesPoints(
            engagementMultiplier = engagementMultiplier.toDouble(),
            closedIssues = closedIssues.toDouble(),
            openedIssues = openedIssues.toDouble()
        )

        val pullRequestPoints = calculatePullRequestsPoints(
            engagementMultiplier = engagementMultiplier.toDouble(),
            mergedPullRequests = mergedPullRequests.toDouble(),
            proposedPullRequests = proposedPullRequests.toDouble()
        )

        return (issuesPoints + pullRequestPoints) / 100
    }

    private fun calculateIssuesPoints(
        engagementMultiplier: Double,
        closedIssues: Double,
        openedIssues: Double
    ) = (closedIssues * engagementMultiplier) + openedIssues

    private fun calculatePullRequestsPoints(
        engagementMultiplier: Double,
        mergedPullRequests: Double,
        proposedPullRequests: Double
    ) = (mergedPullRequests * engagementMultiplier) + proposedPullRequests

    data class Params(
        val owner: String,
        val gitRepoName: String
    )
}