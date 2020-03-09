package br.com.pebmed.data.gitrepo.remote

import br.com.pebmed.data.gitrepo.remote.model.GetRepoListResponse
import br.com.pebmed.data.base.BaseDataSourceImpl
import br.com.pebmed.domain.base.BaseErrorData
import br.com.pebmed.domain.base.FullResultWrapper

class GitRepoRemoteDataSource(
    private val gitRepoApi: GitRepoApi
) : BaseDataSourceImpl() {
    suspend fun getGitRepoList(
        page: Int,
        language: String
    ): FullResultWrapper<GetRepoListResponse, BaseErrorData<Unit>> {
        return safeApiCall { gitRepoApi.getGitRepoList(page, language) }
    }
}