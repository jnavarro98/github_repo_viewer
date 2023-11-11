package com.janavarro.githubrepoviewer.base

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import com.janavarro.githubrepoviewer.ui.repolist.model.ParcelableGithubRepo

fun Intent.getParcelableApiAware(
    key: String,
    parcelable: Class<ParcelableGithubRepo>
): Parcelable? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, parcelable)
    } else {
        getParcelableExtra(key)
    }