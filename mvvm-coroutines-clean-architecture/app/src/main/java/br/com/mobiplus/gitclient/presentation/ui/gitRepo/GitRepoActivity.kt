package br.com.mobiplus.gitclient.presentation.ui.gitRepo

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mobiplus.gitclient.R
import br.com.mobiplus.gitclient.presentation.extensions.replaceFragment
import br.com.mobiplus.gitclient.presentation.extensions.setGone
import br.com.mobiplus.gitclient.presentation.extensions.setVisible
import br.com.mobiplus.gitclient.presentation.extensions.showToast
import br.com.mobiplus.gitclient.presentation.ui.base.Navigator
import br.com.mobiplus.gitclient.presentation.ui.base.ViewState
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.GitRepoListViewModel
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.adapter.GitRepoAdapterListener
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.adapter.GitRepoListAdapter
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model.GitRepoUIModel
import br.com.mobiplus.gitclient.presentation.ui.pullRequest.list.PullRequestListActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_git_repo.*
import kotlinx.android.synthetic.main.activity_git_repo_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitRepoActivity
    : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun open(from: Context, owner: String, gitRepoName: String) {
            val bundle = Bundle()

            bundle.putString("ownerModel", owner)
            bundle.putString("gitRepoName", gitRepoName)

            Navigator.goToActivity(from, GitRepoActivity::class.java, bundle)
        }
    }

    private lateinit var owner: String
    private lateinit var gitRepoName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_repo)
        bottom_navigation_detail.setOnNavigationItemSelectedListener(this)

        owner = intent.extras?.getString("ownerModel") ?: ""
        gitRepoName = intent.extras?.getString("gitRepoName") ?: ""
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.bottom_nav_detail -> {
                Toast.makeText(this, "Bottom Detail", Toast.LENGTH_LONG).show()
                //replaceFragment(PullRequestListActivity.newInstance(), R.id.content)
                true
            }
            R.id.bottom_nav_pull_request -> {
                replaceFragment(PullRequestListActivity.newInstance(owner, gitRepoName), R.id.container_detail)
                true
            }
            else -> false
        }
    }
    //endregion
}
