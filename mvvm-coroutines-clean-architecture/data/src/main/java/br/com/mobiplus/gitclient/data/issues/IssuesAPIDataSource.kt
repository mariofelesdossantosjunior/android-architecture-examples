package br.com.mobiplus.gitclient.data.issues

import br.com.mobiplus.gitclient.data.base.BaseDataSourceImpl
import br.com.mobiplus.gitclient.data.issues.model.IssuesResponseModel
import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.FullResultWrapper
import br.com.mobiplus.gitclient.domain.model.GithubError

class IssuesAPIDataSource(
    private val issuesApi: IssuesApi
) : BaseDataSourceImpl() {
    suspend fun getIssuesList(
        owner: String,
        gitRepoName: String
    ): FullResultWrapper<List<IssuesResponseModel>, BaseErrorData<GithubError>> {
        return safeApiCall { issuesApi.getIssuesList(owner, gitRepoName) }
    }
}