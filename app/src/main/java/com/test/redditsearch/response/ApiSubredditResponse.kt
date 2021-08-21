package com.test.redditsearch.response

import com.google.gson.annotations.SerializedName
import com.test.redditsearch.subreddit.Subreddit
import java.io.Serializable

/**
 * Data model for generic API responses.
 * @author Julius Villagracia
 */
data class ApiSubredditResponse(
    @SerializedName("kind")
    val kind: String? = "",
    @SerializedName("data")
    val data: Subreddit
): Serializable