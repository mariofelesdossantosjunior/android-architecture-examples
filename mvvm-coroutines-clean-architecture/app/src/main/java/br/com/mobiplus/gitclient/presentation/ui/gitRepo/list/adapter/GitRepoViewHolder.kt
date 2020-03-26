package br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.adapter

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.mobiplus.gitclient.R
import br.com.mobiplus.gitclient.presentation.extensions.isValidForGlide
import br.com.mobiplus.gitclient.presentation.extensions.setGone
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model.GitRepoUIModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_repo_list.view.*

class GitRepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(activity: Activity, gitRepo: GitRepoUIModel, listener: GitRepoAdapterListener) {
        itemView.textRepoName.text = gitRepo.name
        itemView.textRepoAuthor.text = gitRepo.ownerLogin
        itemView.textRepoDescription.text = gitRepo.description
        itemView.textStarsCount.text = gitRepo.stargazersCount.toString()
        itemView.textForksCount.text = gitRepo.forksCount.toString()
        itemView.textIssuesCount.text = gitRepo.openIssuesCount.toString()

        gitRepo.reliabilityFactor?.let {
            if (it.enabled) {
                itemView.textReliability.text = it.data
            } else {
                itemView.textReliability.setGone()
            }
        }

        if (activity.isValidForGlide()) {
            Glide.with(activity)
                .load(gitRepo.ownerAvatarUrl)
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(itemView.imageRepoAuthor)
        }

        itemView.setOnClickListener {
            listener.onItemClick(gitRepo)
        }
    }
}