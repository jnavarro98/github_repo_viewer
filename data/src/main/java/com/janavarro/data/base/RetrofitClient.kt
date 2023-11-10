package com.janavarro.data.base
import com.janavarro.data.githubrepo.repository.GithubApiRetrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.github.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val githubRepo: GithubApiRetrofit by lazy {
        retrofit.create(GithubApiRetrofit::class.java)
    }

}





