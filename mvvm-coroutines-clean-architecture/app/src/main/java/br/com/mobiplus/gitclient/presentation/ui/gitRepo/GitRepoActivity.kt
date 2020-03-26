package br.com.mobiplus.gitclient.presentation.ui.gitRepo

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.mobiplus.gitclient.R
import br.com.mobiplus.gitclient.presentation.extensions.replaceFragment
import br.com.mobiplus.gitclient.presentation.ui.base.Navigator
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.details.GitRepoDetailFragment
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model.GitRepoUIModel
import br.com.mobiplus.gitclient.presentation.ui.issues.list.IssuesListFragment
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.list.PullRequestListFragment
import br.com.mobiplus.gitclient.presentation.util.Constants.Companion.GIT_REPO_UI_MODEL
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_git_repo.*

class GitRepoActivity
    : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun open(from: Context, gitRepoIUModel: GitRepoUIModel) {
            val bundle = Bundle()

            bundle.putSerializable(GIT_REPO_UI_MODEL, gitRepoIUModel)

            Navigator.goToActivity(from, GitRepoActivity::class.java, bundle)
        }
    }

    private lateinit var repoUIModel: GitRepoUIModel
    private lateinit var owner: String
    private lateinit var gitRepoName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_repo)
        bottom_navigation_detail.setOnNavigationItemSelectedListener(this)

        val gitRepo = intent.getSerializableExtra(GIT_REPO_UI_MODEL) as GitRepoUIModel

        gitRepo.let {
            owner = it.ownerLogin.toString()
            gitRepoName = it.name.toString()
            repoUIModel = it
        }

        this.initToolbar()

        replaceFragment(GitRepoDetailFragment.open(repoUIModel), R.id.container_detail)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Details Repository"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.bottom_nav_detail -> {
                replaceFragment(GitRepoDetailFragment.open(repoUIModel), R.id.container_detail)
                true
            }
            R.id.bottom_nav_pull_request -> {
                replaceFragment(
                    PullRequestListFragment.open(owner, gitRepoName),
                    R.id.container_detail
                )
                true
            }
            R.id.bottom_nav_issues -> {
                replaceFragment(IssuesListFragment.open(owner, gitRepoName), R.id.container_detail)
                true
            }
            else -> false
        }
    }
    //endregion
}
