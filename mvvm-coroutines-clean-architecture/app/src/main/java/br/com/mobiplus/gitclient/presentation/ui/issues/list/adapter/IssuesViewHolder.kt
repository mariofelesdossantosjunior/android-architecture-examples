package br.com.mobiplus.gitclient.presentation.ui.issues.list.adapter

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.mobiplus.gitclient.domain.model.IssuesModel
import kotlinx.android.synthetic.main.item_pull_request_list.view.*

class IssuesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(
        issues: IssuesModel,
        listener: IssuesListAdapterListener
    ) {
        itemView.textTitle.text = issues.title
        itemView.textDescription.text = issues.body

        itemView.setOnClickListener {
            listener.onItemClick(issues.id)
        }
    }
}