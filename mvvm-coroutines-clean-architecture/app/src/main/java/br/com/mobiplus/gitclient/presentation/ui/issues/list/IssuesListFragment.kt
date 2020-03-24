package br.com.mobiplus.gitclient.presentation.ui.issues.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mobiplus.gitclient.R
import br.com.mobiplus.gitclient.domain.model.IssuesModel
import br.com.mobiplus.gitclient.presentation.extensions.setGone
import br.com.mobiplus.gitclient.presentation.extensions.setVisible
import br.com.mobiplus.gitclient.presentation.extensions.showToast
import br.com.mobiplus.gitclient.presentation.ui.base.ViewState
import br.com.mobiplus.gitclient.presentation.ui.issues.list.adapter.IssuesListAdapter
import br.com.mobiplus.gitclient.presentation.ui.issues.list.adapter.IssuesListAdapterListener
import kotlinx.android.synthetic.main.fragment_issues_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class IssuesListFragment() : Fragment() {

    companion object {
        fun open(owner: String, gitRepoName: String): Fragment {
            val bundle = Bundle()

            bundle.putString("ownerModel", owner)
            bundle.putString("gitRepoName", gitRepoName)

            return IssuesListFragment().apply {
                this.arguments = bundle
            }
        }
    }

    private lateinit var owner: String
    private lateinit var gitRepoName: String
    private val viewModel by viewModel<IssuesListViewModel>()
    private lateinit var adapter: IssuesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        owner = arguments?.getString("ownerModel") ?: ""
        gitRepoName = arguments?.getString("gitRepoName") ?: ""

        return inflater.inflate(R.layout.fragment_issues_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.initRecyclerView()
        this.initObservers()
        this.initListeners()

        viewModel.loadIssuesList(
            owner = owner,
            gitRepoName = gitRepoName
        )
    }

    private fun initObservers() {
        viewModel.issuesListState.observe(this, Observer {
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
        buttonIssuesTryAgain.setOnClickListener {
            viewModel.loadIssuesList(
                owner = "",
                gitRepoName = ""
            )
        }
    }

    private fun initRecyclerView() {
        activity?.let {
            adapter = IssuesListAdapter(
                it,
                mutableListOf(),
                object : IssuesListAdapterListener {
                    override fun onItemClick(issuesNumber: Long) {
                        context?.let {
//                            IssuesActivity.open(
//                                it,
//                                owner,
//                                gitRepoName,
//                                issuesNumber
//                            )
                        }

                    }
                }
            )
        }


        recyclerViewIssues.layoutManager = LinearLayoutManager(context)
        recyclerViewIssues.adapter = adapter
    }

    //region ViewStates
    private fun showLoadingView() {
        hideErrorView()
        hideContent()

        layoutIssuesLoading.setVisible()
    }

    private fun hideLoadingView() {
        layoutIssuesLoading.setGone()
    }

    private fun showErrorView(message: String) {
        hideLoadingView()
        hideContent()

        textIssuesError.text = message
        layoutIssuesError.setVisible()
    }

    private fun hideErrorView() {
        layoutIssuesError.setGone()
        textIssuesError.text = ""
    }

    private fun showContent(issuesList: List<IssuesModel>) {
        hideLoadingView()
        hideErrorView()

        adapter.addItems(issuesList)

        recyclerViewIssues.setVisible()
    }

    private fun hideContent() {
        recyclerViewIssues.setGone()
    }
    //endregion
}
