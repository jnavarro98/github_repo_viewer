package com.janavarro.domain.githubrepo.model

data class GithubRepo(
        val name: String?,
        val description: String?,
        val stars: Int?,
        val forksCount: Int?,
        val language: String?,
        val url: String?
)