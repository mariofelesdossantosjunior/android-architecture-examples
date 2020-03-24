package br.com.mobiplus.gitclient.domain.model

data class IssuesModel(
    val id: Long,
    val title: String,
    val body: String?
)