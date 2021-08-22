package com.test.redditsearch.subreddit

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Model class for subreddit object
 * @author Julius Villagracia
 */
data class Subreddit(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("subreddit_name_prefixed")
    val subredditNamePrefixed: String? = "",
    @SerializedName("permalink")
    val permalink: String? = "",
    @SerializedName("thumbnail")
    val thumbnail: String? = "",
    @SerializedName("author")
    val author: String? = "",
    @SerializedName("display_name_prefixed")
    val displayNamePrefixed: String? = "",

): Serializable