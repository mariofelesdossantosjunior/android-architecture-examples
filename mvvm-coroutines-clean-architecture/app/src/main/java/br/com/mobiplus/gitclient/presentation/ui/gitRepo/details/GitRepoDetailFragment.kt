package br.com.mobiplus.gitclient.presentation.ui.gitRepo.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.mobiplus.gitclient.R
import br.com.mobiplus.gitclient.presentation.extensions.loadImage
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model.GitRepoUIModel
import br.com.mobiplus.gitclient.presentation.util.Constants.Companion.GIT_REPO_UI_MODEL
import kotlinx.android.synthetic.main.fragment_git_repo_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitRepoDetailFragment : Fragment() {

    companion object {
        fun open(gitRepoUIModel: GitRepoUIModel): Fragment {
            val bundle = Bundle()

            bundle.putSerializable(GIT_REPO_UI_MODEL, gitRepoUIModel)

            return GitRepoDetailFragment().apply {
                this.arguments = bundle
            }
        }
    }

    private lateinit var gitRepoUIModel: GitRepoUIModel
    private val viewModel by viewModel<GitRepoDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gitRepoUIModel = arguments?.getSerializable(GIT_REPO_UI_MODEL) as GitRepoUIModel

        return inflater.inflate(R.layout.fragment_git_repo_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.initObservers()

        viewModel.loadRepoDetails(gitRepoUIModel)
    }

    private fun initObservers() {
        viewModel.gitRepoUIDetails.observe(this, Observer {
            textTitle.text = it.name
            textDescription.text = it.description
            loadImage(it.ownerAvatarUrl ?: "", imageGitRepo)
        })
    }
}
