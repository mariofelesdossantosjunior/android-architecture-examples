package br.com.mobiplus.gitclient.data.issues

import br.com.mobiplus.gitclient.data.issues.model.IssuesResponseModel
import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.FullResultWrapper
import br.com.mobiplus.gitclient.domain.model.GithubError
import br.com.mobiplus.gitclient.domain.model.IssuesModel
import br.com.mobiplus.gitclient.domain.repository.IssuesRepository

class IssuesRepositoryImpl(
    private val issuesAPIDataSource: IssuesAPIDataSource
) : IssuesRepository {
    override suspend fun getIssuesList(
        owner: String,
        gitRepoName: String
    ): FullResultWrapper<List<IssuesModel>, BaseErrorData<GithubError>> {
        val listIssues = issuesAPIDataSource.getIssuesList(owner, gitRepoName)

        return listIssues.transformSuccess(handleGetIssuesSuccess())
    }

    private fun handleGetIssuesSuccess(): (List<IssuesResponseModel>) -> List<IssuesModel> {
        return {
            it.map { issuesResponse ->
                issuesResponse.mapTo()
            }
        }
    }
}