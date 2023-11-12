package com.janavarro.githubrepoviewer.ui.repolist.presenter

import com.janavarro.domain.githubrepo.interactor.GithubListInteractor
import com.janavarro.githubrepoviewer.base.BasePresenter
import com.janavarro.githubrepoviewer.ui.repolist.activity.RepoListView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class RepoListPresenter(
    private val githubListInteractor: GithubListInteractor,
    private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : BasePresenter<RepoListView>() {

    private val uiScope = CoroutineScope(uiDispatcher)

    //TODO: For pagination we would handle in the Recyclerview scroll event api calls to the github api with proper logic
    fun getRepoList(repo: String) {
        uiScope.launch {
            view?.showProgress()
            val response = reposByUsername(repo)
            if (response.isSuccessful()) {
                response.result?.let { view?.showRepos(it) }
                view?.hideProgress()
            } else {
                when(response.exception) {
                    is UnknownHostException -> {
                        view?.showNetworkError()
                    }
                    else -> view?.showGenericError()
                }
            }
        }
    }

    private suspend fun reposByUsername(username: String) = withContext(ioDispatcher) {
        githubListInteractor.getRepos(username)
    }

}