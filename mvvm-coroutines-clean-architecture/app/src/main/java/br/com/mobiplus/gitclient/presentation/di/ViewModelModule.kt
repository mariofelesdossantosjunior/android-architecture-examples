package br.com.mobiplus.gitclient.presentation.di

import br.com.mobiplus.gitclient.domain.usecases.GetGitRepoListUseCase
import br.com.mobiplus.gitclient.presentation.ui.main.MainViewModel
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.list.PullRequestListViewModel
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.details.PullRequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            get() as GetGitRepoListUseCase
        )
    }

    viewModel {
        PullRequestListViewModel(
            getPullRequestsUseCase = get()
        )
    }

    viewModel {
        PullRequestViewModel(
            getPullRequestUseCase = get()
        )
    }
}