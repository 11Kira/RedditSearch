package com.test.redditsearch.core.modules

import com.test.redditsearch.subreddit.SubRedditApi
import com.test.redditsearch.subreddit.SubRedditRepo
import org.koin.dsl.module

val repositoryModule = module {
    factory { provideSubRedditRepo(get()) }
}

fun provideSubRedditRepo(subRedditApi: SubRedditApi) = SubRedditRepo(subRedditApi)