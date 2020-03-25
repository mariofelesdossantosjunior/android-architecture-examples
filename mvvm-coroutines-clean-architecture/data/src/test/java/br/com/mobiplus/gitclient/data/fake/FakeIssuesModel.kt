package br.com.mobiplus.gitclient.data.fake

import br.com.mobiplus.gitclient.data.issues.model.IssuesResponseModel
import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.base.resultwrapper.FullResultWrapper
import br.com.mobiplus.gitclient.domain.model.GithubError

class FakeIssuesModel {
    companion object {
        fun mockResult() = FullResultWrapper<List<IssuesResponseModel>, BaseErrorData<GithubError>>(
            success = listOf(
                IssuesResponseModel(
                    id = 1,
                    title = "Title",
                    body = "Body"
                )
            )
        )

        fun mockResultTransformed() = mockResult().transformSuccess {
            it.map { issuesResponse ->
                issuesResponse.mapTo()
            }
        }
    }
}