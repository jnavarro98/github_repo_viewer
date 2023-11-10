package com.janavarro.data.githubrepo.model

import com.google.gson.annotations.SerializedName

data class ApiGithubRepo (
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val stargazersCount: Int?
)