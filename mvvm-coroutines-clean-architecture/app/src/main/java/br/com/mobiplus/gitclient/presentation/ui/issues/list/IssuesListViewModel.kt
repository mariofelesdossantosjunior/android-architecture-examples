package br.com.mobiplus.gitclient.presentation.ui.issues.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.model.GithubError
import br.com.mobiplus.gitclient.domain.model.IssuesModel
import br.com.mobiplus.gitclient.domain.usecases.GetIssuesUseCase
import br.com.mobiplus.gitclient.presentation.extensions.loadViewState
import br.com.mobiplus.gitclient.presentation.ui.base.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssuesListViewModel(
    private val getIssuesUseCase: GetIssuesUseCase
) : ViewModel() {
    private val _issuesListState =
        MutableLiveData<ViewState<List<IssuesModel>, BaseErrorData<GithubError>>>()
    val issuesListState: LiveData<ViewState<List<IssuesModel>, BaseErrorData<GithubError>>>
        get() = _issuesListState

    fun loadIssuesList(owner: String, gitRepoName: String) {
        _issuesListState.postValue(ViewState.Loading())

        viewModelScope.launch(Dispatchers.IO) {
            val params = GetIssuesUseCase.Params(owner, gitRepoName)
            val resultWrapper = getIssuesUseCase.runAsync(params)

            val viewState = loadViewState(resultWrapper)
            _issuesListState.postValue(viewState)
        }
    }
}