package com.janavarro.githubrepoviewer.ui.repolist.activity

import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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

    override fun showNetworkError() {
        loadError(R.drawable.network_error, getString(R.string.network_error))
    }

    override fun showGenericError() {
        loadError(R.drawable.ic_generic_error, getString(R.string.generic_error))
    }

    override fun showProgress() {
        Glide.with(baseContext).asGif()
            .load(R.raw.loading_gif)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(binding.viewStatus.ivInfoIcon)
        binding.apply {
            viewStatus.tvMessage.visibility = View.GONE
            viewStatus.root.visibility = View.VISIBLE
            viewStatus.root.startAnimation(AnimationUtils.loadAnimation(baseContext, R.anim.fade_in))
        }
    }

    override fun hideProgress() {
        binding.apply {
            viewStatus.tvMessage.visibility = View.GONE
            viewStatus.root.visibility = View.GONE
            viewStatus.root.startAnimation(AnimationUtils.loadAnimation(baseContext, R.anim.fade_out))

        }
    }

    private fun loadError(image: Int, message: String) {
        Glide.with(baseContext)
            .load(image)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .fitCenter()
            .into(binding.viewStatus.ivInfoIcon)
        binding.apply {
            viewStatus.tvMessage.text = message
            viewStatus.tvMessage.visibility = View.VISIBLE
            viewStatus.root.visibility = View.VISIBLE
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}