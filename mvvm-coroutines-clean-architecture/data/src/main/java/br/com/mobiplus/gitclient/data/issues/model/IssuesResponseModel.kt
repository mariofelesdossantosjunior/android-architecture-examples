package br.com.mobiplus.gitclient.data.issues.model

import br.com.mobiplus.gitclient.domain.model.IssuesModel
import com.google.gson.annotations.SerializedName

data class IssuesResponseModel(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String?
) {
    fun mapTo() = IssuesModel(
        id = this.id,
        title = this.title,
        body = this.body
    )
}