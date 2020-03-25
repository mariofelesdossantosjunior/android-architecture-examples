package br.com.mobiplus.gitclient.domain

import br.com.mobiplus.gitclient.domain.usecases.GetIssuesUseCase

class FakeGetIssuesUseCase {
    class Params {
        companion object {
            fun mock() = GetIssuesUseCase.Params(
                owner = "OwnerModel",
                gitRepoName = "RepoName"
            )
        }
    }
}