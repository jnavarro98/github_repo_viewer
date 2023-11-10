package com.janavarro.domain.githubrepo.interactor

import com.janavarro.domain.base.InteractorResult
import com.janavarro.domain.githubrepo.model.GithubRepo

interface GithubListInteractor {
    fun getRepos(username: String): InteractorResult<List<GithubRepo>>
}