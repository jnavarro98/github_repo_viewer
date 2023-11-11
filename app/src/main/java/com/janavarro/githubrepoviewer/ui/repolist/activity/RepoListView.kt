package com.janavarro.githubrepoviewer.ui.repolist.activity

import com.janavarro.domain.githubrepo.model.GithubRepo
import com.janavarro.githubrepoviewer.base.BaseView

interface RepoListView : BaseView {

    fun showRepos(repos: List<GithubRepo>)
}