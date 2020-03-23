package br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model

import br.com.mobiplus.gitclient.domain.model.GitRepoModel
import java.util.*

data class GitRepoUIModel(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var stargazersCount: Int? = null,
    var forksCount: Int? = null,
    var reliabilityFactor: String? = null,
    var language: String? = null,
    var openIssuesCount: Int? = null,
    var ownerLogin: String? = null,
    var ownerAvatarUrl: String? = null
) {
    fun mapFrom(from: GitRepoModel) {
        this.id = from.id
        this.name = from.name
        this.description = from.description
        this.stargazersCount = from.stargazersCount
        this.forksCount = from.forksCount

        from.reliabilityFactor?.let {
            this.reliabilityFactor = String.format(Locale.US, "%.2f", it)
        }

        this.language = from.language
        this.openIssuesCount = from.openIssuesCount
        this.ownerLogin = from.ownerModel.login
        this.ownerAvatarUrl = from.ownerModel.avatarUrl
    }
}
