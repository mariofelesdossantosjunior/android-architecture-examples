package br.com.mobiplus.gitclient.presentation.ui.issues.list.model

import android.os.Parcelable
import br.com.mobiplus.gitclient.domain.model.IssuesModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IssueUIModel(
    var id: Long? = null,
    var title: String? = null,
    var body: String? = null
) : Parcelable {
    fun mapFrom(from: IssuesModel) {
        this.id = from.id
        this.title = from.title
        this.body = from.body
    }
}