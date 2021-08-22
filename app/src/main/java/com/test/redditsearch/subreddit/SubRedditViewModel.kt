package com.test.redditsearch.subreddit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.redditsearch.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.inject

/**
 * ViewModel class for the subreddit API calls
 * @author Julius Villagracia
 */
class SubRedditViewModel : BaseViewModel() {

    private val subRedditRepo: SubRedditRepo by inject()
    private val _events = MutableLiveData<UIEvent>()
    val events: LiveData<UIEvent> = _events

    /**
     * Retrieves all available subreddit
     */
    fun retrieveSubreddits() {
        _events.value = SubRedditEvent.OnStartLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = kotlin.runCatching { subRedditRepo.retrieveSubReddits() }
            withContext(Dispatchers.Main) {
                result.onSuccess { response ->
                    response?.data?.children?.let { subreddits ->
                        if (subreddits.isEmpty()) {
                            _events.value = SubRedditEvent.OnNoAvailable
                        } else {
                            _events.value = SubRedditEvent.OnFinishedLoading(subreddits)
                        }
                    }
                }.onFailure { error ->
                    _events.value = SubRedditEvent.OnFailedFetching(error.message.toString())
                }
            }
        }
    }

    /**
     * Search for specific subreddit
     * @param searchQuery The search query to be passed to API
     * @param type The type of data to be retrieved
     */
    fun searchSubreddits(searchQuery: String, type: String) {
        _events.value = SubRedditSearchEvent.OnStartLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = kotlin.runCatching { subRedditRepo.searchSubReddit(searchQuery, type) }
            withContext(Dispatchers.Main) {
                result.onSuccess { response ->
                    response?.data?.children?.let { subreddits ->
                        if (subreddits.isEmpty()) {
                            _events.value = SubRedditSearchEvent.OnNoAvailable
                        } else {
                            _events.value = SubRedditSearchEvent.OnFinishedLoadingSearchResults(subreddits)
                        }
                    }
                }.onFailure { error ->
                    _events.value = SubRedditSearchEvent.OnFailedFetching(error.message.toString())
                }
            }
        }
    }

    /**
     * Retrieves the subreddit details
     * @param subreddit The subreddit to be retrieved
     */
    fun getSubRedditDetails(subreddit: String) {
        _events.value = SubRedditDetailsEvent.OnStartLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = kotlin.runCatching { subRedditRepo.getSubRedditDetails(subreddit) }
            withContext(Dispatchers.Main) {
                result.onSuccess { response ->
                    response?.data?.children?.let { subreddits ->
                        if (subreddits.isEmpty()) {
                            _events.value = SubRedditDetailsEvent.OnNoAvailable
                        } else {
                            Log.e("testSubReddits", subreddits.toString())
                            _events.value = SubRedditDetailsEvent.OnFinishedLoadingDetails(subreddits)
                        }
                    }
                }.onFailure { error ->
                    _events.value = SubRedditDetailsEvent.OnFailedFetching(error.message.toString())
                }
            }
        }
    }
}