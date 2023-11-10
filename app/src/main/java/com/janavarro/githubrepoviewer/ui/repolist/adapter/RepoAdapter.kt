package com.janavarro.githubrepoviewer.ui.repolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janavarro.domain.githubrepo.model.GithubRepo
import com.janavarro.githubrepoviewer.databinding.ViewRepoItemBinding

class RepoAdapter(
    private val clickAction: (GithubRepo) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<GithubRepo> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ViewRepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RepoViewHolder).onBind(
            items[position],
            clickAction
        )
    }

    fun addItems(repos: List<GithubRepo>) {
        val sortedRepos = repos.sortedByDescending { it.stars }
        items.addAll(sortedRepos)
        notifyItemRangeInserted(0, items.size)
    }


}