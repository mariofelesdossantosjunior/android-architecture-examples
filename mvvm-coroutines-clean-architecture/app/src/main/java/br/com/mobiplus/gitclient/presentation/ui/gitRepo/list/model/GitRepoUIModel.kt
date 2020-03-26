package br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model

import br.com.mobiplus.gitclient.domain.model.FeatureFlagModel
import br.com.mobiplus.gitclient.domain.model.GitRepoModel
import java.io.Serializable
import java.util.*

data class GitRepoUIModel(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var stargazersCount: Int? = null,
    var forksCount: Int? = null,
    var reliabilityFactor: FeatureFlagModel<String>? = null,
    var language: String? = null,
    var openIssuesCount: Int? = null,
    var ownerLogin: String? = null,
    var ownerAvatarUrl: String? = null
) : Serializable {
    fun mapFrom(from: GitRepoModel) {
        this.id = from.id
        this.name = from.name
        this.description = from.description
        this.stargazersCount = from.stargazersCount
        this.forksCount = from.forksCount

        from.reliabilityFactor?.let {
            this.reliabilityFactor = FeatureFlagModel<String>(
                enabled = it.enabled,
                data = String.format(Locale.US, "%.2f", it.data)
            )
        }

        this.language = from.language
        this.openIssuesCount = from.openIssuesCount
        this.ownerLogin = from.ownerModel.login
        this.ownerAvatarUrl = from.ownerModel.avatarUrl
    }
}
