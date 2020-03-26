package br.com.mobiplus.gitclient.domain.model

data class GitRepoModel(
    val description: String? = null,
    val disabled: Boolean? = null,
    val fork: Boolean? = null,
    val forks: Int? = null,
    val forksCount: Int? = null,
    val forksUrl: String? = null,
    val fullName: String? = null,
    val id: Int,
    val language: String? = null,
    val name: String? = null,
    val openIssues: Int? = null,
    val openIssuesCount: Int? = null,
    val ownerModel: OwnerModel,
    val score: Double? = null,
    val updatedAt: String? = null,
    val url: String? = null,
    val watchers: Int? = null,
    val watchersCount: Int? = null,
    val stargazersCount: Int? = null,
    var reliabilityFactor: FeatureFlagModel<Double>? = null
)