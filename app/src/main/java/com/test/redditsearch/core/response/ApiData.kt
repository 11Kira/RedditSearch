package com.test.redditsearch.core.response

import com.google.gson.annotations.SerializedName
import com.test.redditsearch.core.response.ApiSubredditResponse
import java.io.Serializable

/**
 * Data model for generic API responses.
 * @author Julius Villagracia
 */
data class ApiData<T>(
    @SerializedName("before")
    val before: String? = "",
    @SerializedName("after")
    val after: String? = "",
    @SerializedName("children")
    val children: T
): Serializable