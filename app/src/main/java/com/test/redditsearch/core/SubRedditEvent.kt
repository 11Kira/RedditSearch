package com.test.redditsearch.core

import com.test.redditsearch.core.response.ApiSubredditResponse
import com.test.redditsearch.subreddit.Subreddit

/**
 * Events for SubRedditViewModel
 * @author Julius Villagracia
 */
sealed class SubRedditEvent : UIEvent {
    data class OnStartLoading(val success: Boolean) : SubRedditEvent()
    data class OnFailedFetching(val error: String) : SubRedditEvent()
    data class OnFinishedLoading(val subreddits: List<ApiSubredditResponse>) : SubRedditEvent()
    data class OnFinishedLoadingSearchResults(val subreddits: List<ApiSubredditResponse>) : SubRedditEvent()
    object OnNoAvailable: SubRedditEvent()
}