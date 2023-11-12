package com.janavarro.githubrepoviewer.ui.repodetails.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.janavarro.githubrepoviewer.R
import com.janavarro.githubrepoviewer.base.getParcelableApiAware
import com.janavarro.githubrepoviewer.databinding.ActivityRepoDetailsBinding
import com.janavarro.githubrepoviewer.ui.repolist.model.ParcelableGithubRepo


/*Not using presenter here since this activity has no logic other than showing data from intent*/
class RepoDetailsActivity : ComponentActivity(), RepoDetailsView {

    companion object {

        const val EXTRA_GITHUB_REPO = "github_repo"

        fun newIntent(
            context: Context,
            githubRepo: ParcelableGithubRepo
        ): Intent {
            val intent = Intent(context, RepoDetailsActivity::class.java)
            intent.putExtra(EXTRA_GITHUB_REPO, githubRepo)
            return intent
        }
    }

    private lateinit var binding: ActivityRepoDetailsBinding

    private var githubRepo: ParcelableGithubRepo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getExtras()
        initUI()
        initListeners()
    }

    private fun initUI() {
        if (githubRepo != null) {
            binding.apply {
                Glide.with(root.context)
                    .load(R.drawable.ic_back)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(btBack)
                tvTitle.text = githubRepo?.name
                tvDescription.text = githubRepo?.description
                viewForks.tvValue.text = githubRepo?.forksCount.toString()
                Glide.with(root.context)
                    .load(R.drawable.ic_fork)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(viewForks.ivIcon)
                viewStars.tvValue.text = githubRepo?.stars.toString()
                Glide.with(root.context)
                    .load(R.drawable.ic_star)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(viewStars.ivIcon)
                if (githubRepo?.language != null) {
                    tvLanguage.text = getString(R.string.repo_language, githubRepo?.language)
                } else {
                    tvLanguage.visibility = View.GONE
                }

            }
        } else {
            showGenericError()
            finish()
        }
    }

    private fun initListeners() {
        binding.apply {
            btBack.setOnClickListener {
                finish()
            }
            btOpenRepo.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                //Animation only works in some devices (ROM dependent)
                builder.setStartAnimations(
                    applicationContext,
                    R.anim.slide_up_in,
                    R.anim.slide_down_out
                )
                val intent = builder.build()
                intent.launchUrl(this@RepoDetailsActivity, Uri.parse(githubRepo?.url))
            }
        }
    }

    private fun getExtras() {
        if (intent.extras != null) {
            githubRepo = intent.getParcelableApiAware(
                EXTRA_GITHUB_REPO,
                ParcelableGithubRepo::class.java
            ) as ParcelableGithubRepo
        }
    }

    override fun showGenericError() {
        Glide.with(baseContext)
            .load(R.drawable.ic_generic_error)
            .fitCenter()
            .into(binding.viewStatus.ivInfoIcon)
        binding.apply {
            viewStatus.tvMessage.text = getString(R.string.generic_error)
            viewStatus.tvMessage.visibility = View.VISIBLE
            viewStatus.root.visibility = View.VISIBLE
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}