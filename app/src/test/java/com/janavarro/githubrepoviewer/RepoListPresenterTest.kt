package com.janavarro.githubrepoviewer

import com.janavarro.domain.base.InteractorResult
import com.janavarro.domain.githubrepo.interactor.GithubListInteractor
import com.janavarro.domain.githubrepo.model.GithubRepo
import com.janavarro.githubrepoviewer.ui.repolist.activity.RepoListView
import com.janavarro.githubrepoviewer.ui.repolist.presenter.RepoListPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import java.net.UnknownHostException

class RepoListPresenterTest {

    @Mock
    private lateinit var mockedGithubListInteractor: GithubListInteractor

    @Mock
    private lateinit var mockedView: RepoListView

    private lateinit var presenter: RepoListPresenter

    @Before
    fun setup() {
        openMocks(this)

        presenter = RepoListPresenter(
            mockedGithubListInteractor,
            Dispatchers.Unconfined,
            Dispatchers.Unconfined
        ).apply {
            view = mockedView
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get repo list is successful`() = runBlocking {

        val mockedSuccessfulResponse = InteractorResult(
            listOf(
                GithubRepo(
                    "name",
                    "description",
                    1,
                    1,
                    "language",
                    "url"
                )
            )
        )
        `when`(mockedGithubListInteractor.getRepos("username")).thenReturn(
            mockedSuccessfulResponse
        )

        runTest { presenter.getRepoList("username") }

        verify(mockedView, times(1)).showProgress()
        verify(mockedView, times(1)).showRepos(mockedSuccessfulResponse.result!!)
        verify(mockedView, times(1)).hideProgress()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get repo list has network error`() = runBlocking {

        val mockedUnsuccessfulResponse =
            InteractorResult<List<GithubRepo>>(exception = UnknownHostException())
        `when`(mockedGithubListInteractor.getRepos("username")).thenReturn(
            mockedUnsuccessfulResponse
        )

        runTest { presenter.getRepoList("username") }

        verify(mockedView, times(1)).showProgress()
        verify(mockedView, times(1)).showNetworkError()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get repo list has generic error`() = runBlocking {

        val mockedUnsuccessfulResponse =
            InteractorResult<List<GithubRepo>>(exception = Exception())
        `when`(mockedGithubListInteractor.getRepos("username")).thenReturn(
            mockedUnsuccessfulResponse
        )

        runTest { presenter.getRepoList("username") }

        verify(mockedView, times(1)).showProgress()
        verify(mockedView, never()).showNetworkError()
        verify(mockedView, times(1)).showGenericError()
    }


}