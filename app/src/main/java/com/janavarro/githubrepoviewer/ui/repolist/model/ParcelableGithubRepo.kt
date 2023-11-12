package com.janavarro.githubrepoviewer.ui.repolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableGithubRepo(
        val name: String?,
        val description: String?,
        val stars: Int?,
        val forksCount: Int?,
        val language: String?,
        val url: String?
) : Parcelable
