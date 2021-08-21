package com.test.redditsearch.subreddit

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Subreddit(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("subreddit_name_prefixed")
    val subredditNamePrefixed: String? = "",
    @SerializedName("permalink")
    val permalink: String? = "",
    @SerializedName("url")
    val imageUrl: String? = "",
    @SerializedName("author")
    val author: String? = "",

): Serializable