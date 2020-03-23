package br.com.mobiplus.gitclient.presentation.di

import br.com.mobiplus.gitclient.domain.usecases.GetGitRepoListUseCase
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.details.GitRepoDetailViewModel
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.GitRepoListViewModel
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.details.PullRequestViewModel
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.list.PullRequestListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        GitRepoListViewModel(
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

    viewModel {
        GitRepoDetailViewModel()
    }
}