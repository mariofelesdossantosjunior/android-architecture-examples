package br.com.mobiplus.gitclient.data.di

import br.com.mobiplus.gitclient.data.featureFlag.FeatureFlagDataSource
import br.com.mobiplus.gitclient.data.gitrepo.GitRepoRepositoryImpl
import br.com.mobiplus.gitclient.data.gitrepo.remote.GitRepoAPIDataSource
import br.com.mobiplus.gitclient.data.gitrepo.remote.GitRepoStatsDataSource
import br.com.mobiplus.gitclient.data.issues.IssuesAPIDataSource
import br.com.mobiplus.gitclient.data.issues.IssuesRepositoryImpl
import br.com.mobiplus.gitclient.data.pullRequest.PullRequestAPIDataSource
import br.com.mobiplus.gitclient.data.pullRequest.PullRequestRepositoryImpl
import br.com.mobiplus.gitclient.domain.repository.GitRepoRepository
import br.com.mobiplus.gitclient.domain.repository.IssuesRepository
import br.com.mobiplus.gitclient.domain.repository.PullRequestRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<GitRepoRepository> {
        GitRepoRepositoryImpl(
            get() as GitRepoAPIDataSource,
            get() as GitRepoStatsDataSource,
            get() as FeatureFlagDataSource
        )
    }

    factory<PullRequestRepository> {
        PullRequestRepositoryImpl(
            get() as PullRequestAPIDataSource
        )
    }

    factory<IssuesRepository> {
        IssuesRepositoryImpl(
            get() as IssuesAPIDataSource
        )
    }
}