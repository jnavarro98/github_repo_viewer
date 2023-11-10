package com.janavarro.githubrepoviewer.ui.repolist.adapter

import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.janavarro.domain.githubrepo.model.GithubRepo
import com.janavarro.githubrepoviewer.R
import com.janavarro.githubrepoviewer.databinding.ViewRepoItemBinding

class RepoViewHolder(
    private val binding: ViewRepoItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        item: GithubRepo,
        action: (GithubRepo) -> Unit) {
        binding.apply {
            tvTitle.text = item.name
            tvDescription.text = item.description
            tvStars.text = item.stars.toString()
            root.setOnClickListener {
                action.invoke(item)
            }
            Glide.with(root.context)
                .load(R.drawable.ic_star)
                .fitCenter()
                .into(ivStars)
        }
    }
}


