package br.com.mobiplus.gitclient.presentation.ui.issues.list.adapter

import android.content.Context
import br.com.mobiplus.gitclient.R
import br.com.mobiplus.gitclient.domain.model.IssuesModel
import br.com.mobiplus.gitclient.presentation.ui.base.adapter.BaseListAdapter
import br.com.mobiplus.gitclient.presentation.ui.base.adapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_pull_request_list.view.*


class IssueAdapter constructor(callbacks: (IssuesModel) -> Unit) :
    BaseListAdapter<IssuesModel>(callbacks) {

    override fun getListItemView(context: Context): BaseViewHolder<IssuesModel> =
        ViewHolder(context)

    inner class ViewHolder(context: Context?) : BaseViewHolder<IssuesModel>(context) {
        override fun layoutResId(): Int = R.layout.item_issues_list

        override fun bind(item: IssuesModel) {
            textTitle.text = item.title
            textDescription.text = item.body
        }
    }

}
