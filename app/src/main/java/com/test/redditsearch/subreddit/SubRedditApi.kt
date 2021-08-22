package com.test.redditsearch.subreddit

import com.test.redditsearch.core.response.ApiResponse
import com.test.redditsearch.core.response.ApiSubredditResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API interface for retrieving subreddits list
 * @author Julius Villagracia
 */
interface SubRedditApi {

    @GET("r/all/")
    suspend fun getAllSubreddits(): Response<ApiResponse<List<ApiSubredditResponse>>>

    @GET("r/subreddit/search")
    suspend fun searchSubreddit(
        @Query("q") searchQuery: String?,
        @Query("type") type: String?,
    ): Response<ApiResponse<List<ApiSubredditResponse>>>

    @GET("{subreddit}")
    suspend fun getSubredditDetails(
        @Path("subreddit", encoded=true) subreddit: String?
    ): Response<ApiResponse<List<ApiSubredditResponse>>>
}