package br.com.mobiplus.gitclient.domain.usecases

import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.ResultWrapper
import br.com.mobiplus.gitclient.domain.base.usecase.BaseAsyncUseCase
import br.com.mobiplus.gitclient.domain.model.GitRepoModel
import br.com.mobiplus.gitclient.domain.model.GithubError
import br.com.mobiplus.gitclient.domain.repository.GitRepoRepository
import br.com.mobiplus.gitclient.domain.usecases.GetGitRepoListUseCase.Params

class GetGitRepoListUseCase(
    private val gitRepoRepository: GitRepoRepository,
    private val getGitRepoReliabilityFactorUseCase: GetGitRepoReliabilityFactorUseCase
) : BaseAsyncUseCase<ResultWrapper<List<GitRepoModel>, BaseErrorData<GithubError>>, Params>() {

    override suspend fun runAsync(params: Params): ResultWrapper<List<GitRepoModel>, BaseErrorData<GithubError>> {
        val gitRepoList = gitRepoRepository.getGitRepoList(
            page = 1,
            language = "java"
        )
        return gitRepoList.transformSuccess(transformSuccess())
    }

    private fun transformSuccess(): (List<GitRepoModel>) -> List<GitRepoModel> {
        return { gitRepoList ->
            gitRepoList.map { gitRepoModel ->
                val params = this.loadParams(gitRepoModel)
                val reliabilityResult = getGitRepoReliabilityFactorUseCase.runSync(params)

                reliabilityResult.success?.apply {
                    gitRepoModel.reliabilityFactor = this
                }
            }

            gitRepoList
        }
    }

    private fun loadParams(gitRepoModel: GitRepoModel): GetGitRepoReliabilityFactorUseCase.Params {
        return GetGitRepoReliabilityFactorUseCase.Params(
            owner = gitRepoModel.ownerModel.login!!,
            gitRepoName = gitRepoModel.name!!
        )
    }

    class Params
}