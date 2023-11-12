package com.janavarro.githubrepoviewer.ui.repolist.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.janavarro.domain.githubrepo.model.GithubRepo
import com.janavarro.githubrepoviewer.R
import com.janavarro.githubrepoviewer.databinding.ViewRepoItemBinding

class RepoViewHolder(
        private val binding: ViewRepoItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(
            item: GithubRepo,
            action: (GithubRepo) -> Unit
    ) {
        binding.apply {
            tvTitle.text = item.name
            if (item.description?.isNotBlank() == true) {
                tvDescription.text = item.description
                tvDescription.visibility = View.VISIBLE
            } else {
                tvDescription.visibility = View.GONE
            }
            viewStars.tvValue.text = item.stars.toString()
            root.setOnClickListener {
                action.invoke(item)
            }
            Glide.with(root.context)
                    .load(R.drawable.ic_star)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(viewStars.ivIcon)
        }
    }
}


