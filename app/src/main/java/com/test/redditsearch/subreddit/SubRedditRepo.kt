package com.test.redditsearch.subreddit

/**
 * Repo class for subreddit network calls
 * @author Julius Villagracia
 */
class SubRedditRepo(private val subRedditApi: SubRedditApi) {

    suspend fun retrieveSubReddits() = subRedditApi.getAllSubreddits().body()

    suspend fun searchSubReddit(
        searchQuery: String,
        type: String
    ) = subRedditApi.searchSubreddit(searchQuery, type).body()

    suspend fun getSubRedditDetails(
        subreddit: String,
    ) = subRedditApi.getSubredditDetails(subreddit).body()
}