package com.janavarro.data.githubrepo.repository

import com.janavarro.data.githubrepo.model.ApiGithubRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiRetrofit {

    @GET("users/{userName}/repos")
    fun getRepos(
        @Path("userName") username : String?
    ): Call<List<ApiGithubRepo>>

}