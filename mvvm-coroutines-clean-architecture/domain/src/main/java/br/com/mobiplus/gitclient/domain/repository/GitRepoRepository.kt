package br.com.mobiplus.gitclient.domain.repository

import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.FullResultWrapper
import br.com.mobiplus.gitclient.domain.base.resultwrapper.ResultWrapper
import br.com.mobiplus.gitclient.domain.model.FeatureFlagModel
import br.com.mobiplus.gitclient.domain.model.GitRepoModel
import br.com.mobiplus.gitclient.domain.model.GitRepoStatsModel
import br.com.mobiplus.gitclient.domain.model.GithubError

interface GitRepoRepository {

    suspend fun getGitRepoList(
        page: Int,
        language: String
    ): ResultWrapper<List<GitRepoModel>, BaseErrorData<GithubError>>

    fun getGitRepoStats(
        owner: String,
        gitRepoName: String
    ): FullResultWrapper<GitRepoStatsModel, BaseErrorData<GithubError>>

    fun getGitRepoReliabilityMultiplier(): FeatureFlagModel<Int>
}