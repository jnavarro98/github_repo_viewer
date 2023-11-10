package com.janavarro.domain.githubrepo.interactor

import com.janavarro.data.base.RetrofitClient
import com.janavarro.domain.base.InteractorResult
import com.janavarro.domain.githubrepo.mapper.GithubRepoMapper
import com.janavarro.domain.githubrepo.model.GithubRepo

class GithubListInteractorImpl(
    private val githubRepoMapper: GithubRepoMapper
) : GithubListInteractor {
    override fun getRepos(username: String): InteractorResult<List<GithubRepo>> {

        val request = RetrofitClient.githubRepo.getRepos(username)
        val response = request.execute()
        return InteractorResult(githubRepoMapper.map(response.body())).apply {
            hasError = !response.isSuccessful
        }
    }

}