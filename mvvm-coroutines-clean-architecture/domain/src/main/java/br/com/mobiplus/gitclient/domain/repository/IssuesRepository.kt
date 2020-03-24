package br.com.mobiplus.gitclient.domain.repository

import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.ResultWrapper
import br.com.mobiplus.gitclient.domain.model.GithubError
import br.com.mobiplus.gitclient.domain.model.IssuesModel

interface IssuesRepository {
    suspend fun getIssuesList(
        owner: String,
        gitRepoName: String
    ): ResultWrapper<List<IssuesModel>, BaseErrorData<GithubError>>
}