package com.janavarro.githubrepoviewer.ui.repolist.presenter

import com.janavarro.domain.githubrepo.interactor.GithubListInteractor
import com.janavarro.githubrepoviewer.base.view.BasePresenter
import com.janavarro.githubrepoviewer.ui.repolist.activity.RepoListView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepoListPresenter(
     private val githubListInteractor: GithubListInteractor,
     private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main,
     private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
): BasePresenter<RepoListView>() {

    private val uiScope = CoroutineScope(uiDispatcher)
    fun getRepoList() {
        uiScope.launch {
            val response = reposByUsername("octocat")
            if(!response.hasError && response.result != null) {
                response.result?.let { view?.showRepos(it) }
            } else {
                view?.showError()
            }
        }
    }

    private suspend fun reposByUsername(username: String) = withContext(ioDispatcher) {
        githubListInteractor.getRepos(username)
    }

}