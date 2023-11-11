package com.janavarro.githubrepoviewer.ui.repolist.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.janavarro.domain.githubrepo.model.GithubRepo
import com.janavarro.githubrepoviewer.databinding.ActivityRepoListBinding
import com.janavarro.githubrepoviewer.ui.repodetails.activity.RepoDetailsActivity
import com.janavarro.githubrepoviewer.ui.repolist.adapter.RepoAdapter
import com.janavarro.githubrepoviewer.ui.repolist.model.ParcelableGithubRepo
import com.janavarro.githubrepoviewer.ui.repolist.presenter.RepoListPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepoListActivity : ComponentActivity(), RepoListView {

    private lateinit var binding: ActivityRepoListBinding
    private lateinit var adapter: RepoAdapter

    @Inject
    lateinit var presenter: RepoListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.view = this
        initRv()
        presenter.getRepoList()
    }

    private fun initRv() {
        adapter = RepoAdapter(::onItemClick)
        binding.rvRepos.apply {
            adapter = this@RepoListActivity.adapter
            layoutManager = LinearLayoutManager(this@RepoListActivity)
        }
    }

    override fun showRepos(repos: List<GithubRepo>) {
        adapter.addItems(repos)
    }

    //Only using parcelable data class when sending through intent to avoid extra mapping.
    private fun onItemClick(item: GithubRepo) {
        val intent = RepoDetailsActivity.newIntent(
            this, ParcelableGithubRepo(
                item.name,
                item.description,
                item.stars,
                item.forksCount,
                item.language,
                item.url
            )
        )
        startActivity(intent)
    }

}