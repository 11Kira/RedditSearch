package com.test.redditsearch.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Data model for generic API responses.
 * @author Julius Villagracia
 */
data class ApiData(
    @SerializedName("before")
    val before: String? = "",
    @SerializedName("after")
    val after: String? = "",
    @SerializedName("children")
    val children: List<ApiSubredditResponse>
): Serializable