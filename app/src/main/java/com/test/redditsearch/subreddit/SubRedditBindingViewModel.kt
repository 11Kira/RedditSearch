package com.test.redditsearch.subreddit

import androidx.lifecycle.MutableLiveData
import com.test.redditsearch.core.BaseViewModel

/**
 * Bind viewModel class for subReddit adapter
 * @author Julius Villagracia
 */
class SubRedditBindingViewModel: BaseViewModel() {

    private val title = MutableLiveData<String>()
    private val subredditName = MutableLiveData<String>()
    private val authorName = MutableLiveData<String>()

    fun bind(subreddit: Subreddit) {
        title.value = subreddit.title
        subredditName.value = subreddit.subredditNamePrefixed
        val author = "Posted by: "
        authorName.value = author.plus(subreddit.author)
    }

    fun getTitle(): MutableLiveData<String> {
        return title
    }
    fun getSubredditName(): MutableLiveData<String> {
        return subredditName
    }
    fun getAuthorName(): MutableLiveData<String> {
        return authorName
    }
}