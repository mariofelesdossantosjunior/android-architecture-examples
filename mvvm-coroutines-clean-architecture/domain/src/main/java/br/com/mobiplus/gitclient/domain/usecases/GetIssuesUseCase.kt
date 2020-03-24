package br.com.mobiplus.gitclient.domain.usecases

import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.ResultWrapper
import br.com.mobiplus.gitclient.domain.base.usecase.BaseAsyncUseCase
import br.com.mobiplus.gitclient.domain.model.GithubError
import br.com.mobiplus.gitclient.domain.model.IssuesModel
import br.com.mobiplus.gitclient.domain.repository.IssuesRepository
import br.com.mobiplus.gitclient.domain.usecases.GetIssuesUseCase.Params

class GetIssuesUseCase(
    private val issuesRepository: IssuesRepository
) : BaseAsyncUseCase<ResultWrapper<List<IssuesModel>, BaseErrorData<GithubError>>, Params>() {

    override suspend fun runAsync(params: Params): ResultWrapper<List<IssuesModel>, BaseErrorData<GithubError>> {
        return issuesRepository.getIssuesList(
            owner = params.owner,
            gitRepoName = params.gitRepoName
        )
    }

    data class Params(
        val owner: String,
        val gitRepoName: String
    )
}