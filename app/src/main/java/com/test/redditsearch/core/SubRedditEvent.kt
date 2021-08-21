package com.test.redditsearch.core

import com.test.redditsearch.subreddit.Subreddit

/**
 * Events for SubRedditViewModel
 * @author Julius Villagracia
 */
sealed class SubRedditEvent : UIEvent {
    data class OnStartLoading(val success: Boolean) : SubRedditEvent()
    data class OnFailedFetching(val error: String) : SubRedditEvent()
    data class OnFinishedLoading(val repositories: List<Subreddit>) : SubRedditEvent()
    object OnNoAvailable: SubRedditEvent()
}