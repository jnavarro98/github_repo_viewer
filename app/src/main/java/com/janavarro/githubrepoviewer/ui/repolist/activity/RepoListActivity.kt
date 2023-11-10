package com.janavarro.githubrepoviewer.ui.repolist.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janavarro.domain.githubrepo.model.GithubRepo
import com.janavarro.githubrepoviewer.databinding.ActivityRepoListBinding
import com.janavarro.githubrepoviewer.ui.repolist.adapter.RepoAdapter
import com.janavarro.githubrepoviewer.ui.repolist.presenter.RepoListPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepoListActivity : ComponentActivity(), RepoListView {

    private lateinit var binding : ActivityRepoListBinding
    private lateinit var adapter: RepoAdapter

    @Inject
    lateinit var presenter : RepoListPresenter

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

    private fun onItemClick(item: GithubRepo) {
        Toast.makeText(this, "Repo ${item.name} clicked!", Toast.LENGTH_LONG).show()
    }

}