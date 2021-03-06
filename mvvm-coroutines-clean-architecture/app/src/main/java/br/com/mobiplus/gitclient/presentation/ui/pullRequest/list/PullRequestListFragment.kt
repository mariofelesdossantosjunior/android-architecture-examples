package br.com.mobiplus.gitclient.presentation.ui.pullRequest.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mobiplus.gitclient.R
import br.com.mobiplus.gitclient.domain.model.PullRequestModel
import br.com.mobiplus.gitclient.presentation.extensions.setGone
import br.com.mobiplus.gitclient.presentation.extensions.setVisible
import br.com.mobiplus.gitclient.presentation.extensions.showToast
import br.com.mobiplus.gitclient.presentation.ui.base.Navigator
import br.com.mobiplus.gitclient.presentation.ui.base.ViewState
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model.GitRepoUIModel
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.details.PullRequestActivity
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.list.adapter.PullRequestListAdapter
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.list.adapter.PullRequestListAdapterListener
import kotlinx.android.synthetic.main.fragment_pull_request_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PullRequestListFragment() : Fragment() {

    companion object {
        fun open(owner: String, gitRepoName: String): Fragment {
            val bundle = Bundle()

            bundle.putString("ownerModel", owner)
            bundle.putString("gitRepoName", gitRepoName)

            return PullRequestListFragment().apply {
                this.arguments = bundle
            }
        }
    }

    private lateinit var owner: String
    private lateinit var gitRepoName: String
    private val viewModel by viewModel<PullRequestListViewModel>()
    private lateinit var adapter: PullRequestListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        owner = arguments?.getString("ownerModel") ?: ""
        gitRepoName = arguments?.getString("gitRepoName") ?: ""

        return inflater.inflate(R.layout.fragment_pull_request_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.initRecyclerView()
        this.initObservers()
        this.initListeners()

        viewModel.loadPullRequestList(
            owner = owner,
            gitRepoName = gitRepoName
        )
    }

    private fun initObservers() {
        viewModel.pullRequestListState.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                    showLoadingView()
                }

                is ViewState.Success -> {
                    showContent(it.success)
                }

                is ViewState.Empty -> {
                    val message = getString(R.string.empty_list)

                    when {
                        adapter.isEmpty() -> showErrorView(message)
                        else -> showToast(message)
                    }
                }

                is ViewState.Error -> {
                    showErrorView(it.error.toString())
                }
            }
        })
    }

    private fun initListeners() {
        buttonPullRequestsTryAgain.setOnClickListener {
            viewModel.loadPullRequestList(
                owner = "",
                gitRepoName = ""
            )
        }
    }

    private fun initRecyclerView() {
        activity?.let {
            adapter = PullRequestListAdapter(
                it,
                mutableListOf(),
                object : PullRequestListAdapterListener {
                    override fun onItemClick(pullRequestNumber: Long) {
                        context?.let {
                            PullRequestActivity.open(
                                it,
                                owner,
                                gitRepoName,
                                pullRequestNumber
                            )
                        }

                    }
                }
            )
        }


        recyclerViewPullRequests.layoutManager = LinearLayoutManager(context)
        recyclerViewPullRequests.adapter = adapter
    }

    //region ViewStates
    private fun showLoadingView() {
        hideErrorView()
        hideContent()

        layoutPullRequestsLoading.setVisible()
    }

    private fun hideLoadingView() {
        layoutPullRequestsLoading.setGone()
    }

    private fun showErrorView(message: String) {
        hideLoadingView()
        hideContent()

        textPullRequestsError.text = message
        layoutPullRequestsError.setVisible()
    }

    private fun hideErrorView() {
        layoutPullRequestsError.setGone()
        textPullRequestsError.text = ""
    }

    private fun showContent(pullRequestList: List<PullRequestModel>) {
        hideLoadingView()
        hideErrorView()

        adapter.addItems(pullRequestList)

        recyclerViewPullRequests.setVisible()
    }

    private fun hideContent() {
        recyclerViewPullRequests.setGone()
    }
    //endregion
}
