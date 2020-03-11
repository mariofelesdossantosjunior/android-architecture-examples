package br.com.mobiplus.gitclient.domain.repository

import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.ResultWrapper
import br.com.mobiplus.gitclient.domain.model.GitRepoModel
import br.com.mobiplus.gitclient.domain.model.GithubError

interface GitRepoRepository {

    suspend fun getGitRepoList(
        page: Int,
        language: String
    ): ResultWrapper<List<GitRepoModel>, BaseErrorData<GithubError>>

    suspend fun getGitRepo(
        id: Int
    ): ResultWrapper<GitRepoModel, BaseErrorData<GithubError>>
}