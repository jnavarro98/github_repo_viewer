package com.janavarro.githubrepoviewer.ui.repolist.activity

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.janavarro.domain.githubrepo.model.GithubRepo
import com.janavarro.githubrepoviewer.R
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
        //TODO: Repo could be retrieved from user input
        presenter.getRepoList("google")
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

    //TODO: Add a retry button, then we could hide the error
    override fun showNetworkError() {
        Glide.with(baseContext)
            .load(R.drawable.network_error)
            .fitCenter()
            .into(binding.viewStatus.ivIcon)
        binding.apply {
            viewStatus.tvMessage.text = getString(R.string.network_error)
            viewStatus.tvMessage.visibility = View.VISIBLE
            viewStatus.root.visibility = View.VISIBLE
        }
    }

    override fun showGenericError() {
        Glide.with(baseContext)
            .load(R.drawable.ic_generic_error)
            .fitCenter()
            .into(binding.viewStatus.ivIcon)
        binding.apply {
            viewStatus.tvMessage.text = getString(R.string.generic_error)
            viewStatus.tvMessage.visibility = View.VISIBLE
            viewStatus.root.visibility = View.VISIBLE
        }
    }

    override fun showProgress() {
        Glide.with(baseContext).asGif()
            .load(R.raw.loading_gif)
            .into(binding.viewStatus.ivIcon)
        binding.apply {
            viewStatus.tvMessage.visibility = View.GONE
            viewStatus.root.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        binding.apply {
            viewStatus.tvMessage.visibility = View.GONE
            viewStatus.root.visibility = View.GONE
        }
    }

    //Only using parcelable data class when sending through intent to avoid redundant mapping.
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