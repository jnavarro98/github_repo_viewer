package com.janavarro.githubrepoviewer.ui.repolist.di

import com.janavarro.domain.githubrepo.interactor.GithubListInteractor
import com.janavarro.domain.githubrepo.interactor.GithubListInteractorImpl
import com.janavarro.domain.githubrepo.mapper.GithubRepoMapper
import com.janavarro.githubrepoviewer.ui.repolist.presenter.RepoListPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object RepoListModule {

    @Provides
    fun provideGithubRepoMapper(): GithubRepoMapper {
        return GithubRepoMapper()
    }

    @Provides
    fun provideGithubListInteractor(githubRepoMapper: GithubRepoMapper): GithubListInteractor {
        return GithubListInteractorImpl(githubRepoMapper)
    }

    @Provides
    fun provideRepoListPresenter(githubListInteractor: GithubListInteractor): RepoListPresenter {
        return RepoListPresenter(githubListInteractor)
    }
}