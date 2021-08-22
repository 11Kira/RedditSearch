package com.test.redditsearch.core

import com.test.redditsearch.core.response.ApiSubredditResponse

/**
 * Events for SubReddit details
 * @author Julius Villagracia
 */
sealed class SubRedditDetailsEvent : UIEvent {
    data class OnStartLoading(val success: Boolean) : SubRedditDetailsEvent()
    data class OnFailedFetching(val error: String) : SubRedditDetailsEvent()
    data class OnFinishedLoadingDetails(val subreddits: List<ApiSubredditResponse>) : SubRedditDetailsEvent()
    object OnNoAvailable: SubRedditDetailsEvent()
}