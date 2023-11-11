package com.janavarro.githubrepoviewer.base

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Glide.init(this, GlideBuilder())
    }
}