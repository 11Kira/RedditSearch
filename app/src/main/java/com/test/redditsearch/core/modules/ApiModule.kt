package com.test.redditsearch.core.modules

import com.test.redditsearch.subreddit.SubRedditApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    factory { provideSubRedditApi(get()) }
}

fun provideSubRedditApi(retrofit: Retrofit): SubRedditApi = retrofit.create(SubRedditApi::class.java)