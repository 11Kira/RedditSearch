package com.test.redditsearch.core.modules

import com.test.redditsearch.subreddit.SubRedditViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SubRedditViewModel() }
}