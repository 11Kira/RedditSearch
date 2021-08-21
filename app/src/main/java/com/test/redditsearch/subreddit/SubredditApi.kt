package com.test.redditsearch.subreddit

import com.test.redditsearch.core.response.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * API interface for retrieving subreddits list
 * @author Julius Villagracia
 */
interface SubredditApi {

    @GET("all/")
    suspend fun searchRepositories(
    ): Response<ApiResponse>
}