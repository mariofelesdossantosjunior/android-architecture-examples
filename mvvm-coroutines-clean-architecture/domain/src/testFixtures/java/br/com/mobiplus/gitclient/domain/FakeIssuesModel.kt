package br.com.mobiplus.gitclient.domain

import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.FullResultWrapper
import br.com.mobiplus.gitclient.domain.model.GithubError
import br.com.mobiplus.gitclient.domain.model.IssuesModel

class FakeIssuesModel {
    companion object {
        fun mockResult() = FullResultWrapper<List<IssuesModel>, BaseErrorData<GithubError>>(
            success = listOf(
                IssuesModel(
                    id = 1,
                    title = "Title",
                    body = "Body"
                )
            )
        )

        fun mockResult2() = FullResultWrapper<List<IssuesModel>, BaseErrorData<GithubError>>(
            success = listOf(
                IssuesModel(
                    id = 1,
                    title = "Title2",
                    body = "Body2"
                )
            )
        )
    }
}