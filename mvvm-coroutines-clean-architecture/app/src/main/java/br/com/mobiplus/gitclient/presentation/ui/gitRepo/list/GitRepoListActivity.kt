package br.com.mobiplus.gitclient.presentation.ui.gitRepo.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mobiplus.gitclient.R
import br.com.mobiplus.gitclient.presentation.extensions.setGone
import br.com.mobiplus.gitclient.presentation.extensions.setVisible
import br.com.mobiplus.gitclient.presentation.extensions.showToast
import br.com.mobiplus.gitclient.presentation.ui.base.ViewState
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.GitRepoActivity
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.adapter.GitRepoAdapterListener
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.adapter.GitRepoListAdapter
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model.GitRepoUIModel
import kotlinx.android.synthetic.main.activity_git_repo_list.*

import org.koin.androidx.viewmodel.ext.android.viewModel

class GitRepoListActivity : AppCompatActivity() {
    private val viewModel by viewModel<GitRepoListViewModel>()
    private lateinit var gitRepoAdapter: GitRepoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_repo_list)

        initReposRecyclerView()
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.gitRepoListState.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                    showLoadingView()
                }

                is ViewState.Success -> {
                    showGitRepoList(it.success)
                }

                is ViewState.Empty -> {
                    val message = getString(R.string.empty_list)

                    when {
                        gitRepoAdapter.isEmpty() -> showErrorView(message)
                        else -> showToast(message)
                    }
                }

                is ViewState.Error -> {
                    val baseErrorData = it.error
                    val message = baseErrorData?.errorMessage ?: getString(R.string.error_message)

                    if (gitRepoAdapter.isEmpty()) {
                        showErrorView(message)
                    } else {
                        showToast(message)
                    }
                }
            }
        })
    }

    private fun initListeners() {
        buttonReposTryAgain.setOnClickListener {
            viewModel.loadGitRepoList()
        }
    }

    private fun initReposRecyclerView() {
        gitRepoAdapter = GitRepoListAdapter(
            this,
            mutableListOf(),
            object : GitRepoAdapterListener {
                override fun onItemClick(item: GitRepoUIModel) {
                    GitRepoActivity.open(
                        this@GitRepoListActivity,
                        gitRepoIUModel =  item
                    )
                }

            }
        )

        recyclerViewRepos.layoutManager = LinearLayoutManager(this)
        recyclerViewRepos.adapter = gitRepoAdapter
    }

    //region ViewStates
    private fun showLoadingView() {
        hideErrorView()
        hideReposList()

        layoutReposLoading.setVisible()
    }

    private fun hideLoadingView() {
        layoutReposLoading.setGone()
    }

    private fun showErrorView(message: String) {
        hideLoadingView()
        hideReposList()

        textReposError.text = message
        layoutReposError.setVisible()
    }

    private fun hideErrorView() {
        layoutReposError.setGone()
        textReposError.text = ""
    }

    private fun showGitRepoList(gitRepos: List<GitRepoUIModel>) {
        hideLoadingView()
        hideErrorView()

        gitRepoAdapter.addItems(gitRepos)

        recyclerViewRepos.setVisible()
    }

    private fun hideReposList() {
        recyclerViewRepos.setGone()
    }
    //endregion
}
