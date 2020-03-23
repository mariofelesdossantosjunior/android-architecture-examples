package br.com.mobiplus.gitclient.presentation.ui.gitRepo.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model.GitRepoUIModel

class GitRepoDetailViewModel(
) : ViewModel() {
    private val _gitRepoUIDetails = MutableLiveData<GitRepoUIModel>()
    val gitRepoUIDetails: LiveData<GitRepoUIModel> get() = _gitRepoUIDetails

    fun loadRepoDetails(gitRepoUIModel: GitRepoUIModel) {
        _gitRepoUIDetails.postValue(gitRepoUIModel)
    }

}