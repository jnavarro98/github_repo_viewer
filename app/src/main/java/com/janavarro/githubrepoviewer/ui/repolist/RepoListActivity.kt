package com.janavarro.githubrepoviewer.ui.repolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.janavarro.githubrepoviewer.databinding.ActivityRepoListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding : ActivityRepoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepoListBinding.inflate(layoutInflater)
    }

}