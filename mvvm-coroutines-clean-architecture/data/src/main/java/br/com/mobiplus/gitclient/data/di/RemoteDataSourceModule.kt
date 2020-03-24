package br.com.mobiplus.gitclient.data.di

import br.com.mobiplus.gitclient.data.gitrepo.remote.GitRepoAPIDataSource
import br.com.mobiplus.gitclient.data.gitrepo.remote.GitRepoApi
import br.com.mobiplus.gitclient.data.gitrepo.remote.GitRepoStatsDataSource
import br.com.mobiplus.gitclient.data.issues.IssuesAPIDataSource
import br.com.mobiplus.gitclient.data.issues.IssuesApi
import br.com.mobiplus.gitclient.data.pullRequest.PullRequestAPIDataSource
import br.com.mobiplus.gitclient.data.pullRequest.PullRequestApi
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments")
val apiDataSourceModule = module {
    factory {
        GitRepoAPIDataSource(
            get() as GitRepoApi
        )
    }

    factory {
        GitRepoStatsDataSource(
            get() as GitRepoApi
        )
    }

    factory {
        PullRequestAPIDataSource(
            get() as PullRequestApi
        )
    }

    factory {
        IssuesAPIDataSource(
            get() as IssuesApi
        )
    }
}