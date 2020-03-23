package br.com.mobiplus.gitclient.data.gitrepo.remote

import br.com.mobiplus.gitclient.data.base.BaseDataSourceImpl
import br.com.mobiplus.gitclient.data.gitrepo.remote.model.GitRepoStatsResponseModel
import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.FullResultWrapper
import br.com.mobiplus.gitclient.domain.model.GithubError
import kotlin.random.Random

class GitRepoStatsDataSource(
    private val gitRepoApi: GitRepoApi
) : BaseDataSourceImpl() {
    fun getGitRepoStats(
        owner: String,
        gitRepoName: String
    ): FullResultWrapper<GitRepoStatsResponseModel, BaseErrorData<GithubError>> {
        return generate(owner, gitRepoName)
    }

    private fun generate(
        owner: String,
        gitRepoName: String
    ): FullResultWrapper<GitRepoStatsResponseModel, BaseErrorData<GithubError>> {
        return FullResultWrapper(
            success = GitRepoStatsResponseModel(
                name = gitRepoName,
                closedIssuesOnLastMonth = Random.nextInt(
                    Random.nextInt(0, 500),
                    Random.nextInt(501, 1000)
                ),
                openedIssuesOnLastMonth = Random.nextInt(
                    Random.nextInt(0, 500),
                    Random.nextInt(501, 1000)
                ),
                mergedPullRequestsOnLastMonth = Random.nextInt(
                    Random.nextInt(0, 500),
                    Random.nextInt(501, 1000)
                ),
                proposedPullRequestsOnLastMonth = Random.nextInt(
                    Random.nextInt(0, 500),
                    Random.nextInt(501, 1000)
                )
            )
        )
    }
}