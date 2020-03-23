package br.com.mobiplus.gitclient.presentation.ui.gitRepo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobiplus.gitclient.domain.base.BaseErrorData
import br.com.mobiplus.gitclient.domain.model.GitRepoModel
import br.com.mobiplus.gitclient.domain.model.GithubError
import br.com.mobiplus.gitclient.domain.usecases.GetGitRepoListUseCase
import br.com.mobiplus.gitclient.presentation.extensions.loadViewState
import br.com.mobiplus.gitclient.presentation.ui.base.ViewState
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model.GitRepoUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GitRepoListViewModel(private val getGitRepoListUseCase: GetGitRepoListUseCase) : ViewModel() {
    private val _gitRepoListState =
        MutableLiveData<ViewState<List<GitRepoUIModel>, BaseErrorData<GithubError>>>()
    val gitRepoListState: LiveData<ViewState<List<GitRepoUIModel>, BaseErrorData<GithubError>>>
        get() = _gitRepoListState

    init {
        loadGitRepoList()
    }

    fun loadGitRepoList() {
        _gitRepoListState.postValue(ViewState.Loading())

        viewModelScope.launch(Dispatchers.IO) {
            val params = GetGitRepoListUseCase.Params()
            val resultWrapper = getGitRepoListUseCase
                .runAsync(params)
                .transformSuccess(
                    transformSuccess()
                )

            val viewState = loadViewState(resultWrapper)

            _gitRepoListState.postValue(viewState)
        }
    }

    private fun transformSuccess(): (List<GitRepoModel>) -> List<GitRepoUIModel> {
        return { list ->
            list.map { gitRepoModel ->
                val gitRepoUIModel = GitRepoUIModel()
                gitRepoUIModel.mapFrom(gitRepoModel)
                gitRepoUIModel
            }
        }
    }
}