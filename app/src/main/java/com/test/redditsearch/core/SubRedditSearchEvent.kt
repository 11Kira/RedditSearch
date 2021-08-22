package com.test.redditsearch.core

import com.test.redditsearch.core.response.ApiSubredditResponse

/**
 * Events for SubReddit searching
 * @author Julius Villagracia
 */
sealed class SubRedditSearchEvent : UIEvent {
    data class OnStartLoading(val success: Boolean) : SubRedditSearchEvent()
    data class OnFailedFetching(val error: String) : SubRedditSearchEvent()
    data class OnFinishedLoadingSearchResults(val subreddits: List<ApiSubredditResponse>) : SubRedditSearchEvent()
    object OnNoAvailable: SubRedditSearchEvent()
}