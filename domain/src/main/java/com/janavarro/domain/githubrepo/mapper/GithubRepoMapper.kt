package com.janavarro.domain.githubrepo.mapper

import com.janavarro.data.githubrepo.model.ApiGithubRepo
import com.janavarro.domain.base.Mapper
import com.janavarro.domain.githubrepo.model.GithubRepo

class GithubRepoMapper: Mapper<List<ApiGithubRepo>, List<GithubRepo>> {
    override fun map(apiRepos: List<ApiGithubRepo>?): List<GithubRepo>? {
        val repos = mutableListOf<GithubRepo>()
        apiRepos?.forEach { apiRepo -> repos.add(GithubRepo(
                apiRepo.name ?: "",
                apiRepo.description ?: "",
                apiRepo.stargazersCount ?: 0
        )) }
        return repos
    }

}