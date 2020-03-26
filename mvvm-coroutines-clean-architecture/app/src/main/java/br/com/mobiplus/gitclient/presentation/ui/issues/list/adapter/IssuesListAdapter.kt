package br.com.mobiplus.gitclient.presentation.ui.issues.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mobiplus.gitclient.R
import br.com.mobiplus.gitclient.domain.model.IssuesModel

class IssuesListAdapter(
    private val issuesList: MutableList<IssuesModel>,
    private val listener: IssuesListAdapterListener
) : RecyclerView.Adapter<IssuesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_issues_list, parent, false)
        return IssuesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return issuesList.count()
    }

    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        val item = issuesList[position]

        holder.bindView(item, listener)
    }

    fun addItems(issuesList: List<IssuesModel>) {
        val oldSize = this.issuesList.size
        this.issuesList.addAll(issuesList)
        notifyItemRangeInserted(oldSize, issuesList.size)
    }

    fun addItem(issues: IssuesModel) {
        issuesList.add(issues)
        notifyItemInserted(issuesList.size - 1)
    }

    fun isEmpty() = issuesList.isEmpty()
}