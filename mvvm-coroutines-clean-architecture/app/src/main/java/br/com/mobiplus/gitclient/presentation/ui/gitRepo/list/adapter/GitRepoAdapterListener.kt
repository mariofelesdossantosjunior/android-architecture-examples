package br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.adapter

import br.com.mobiplus.gitclient.presentation.ui.gitRepo.list.model.GitRepoUIModel

interface GitRepoAdapterListener {
    fun onItemClick(item: GitRepoUIModel)
}