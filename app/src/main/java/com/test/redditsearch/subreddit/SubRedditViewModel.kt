package com.test.redditsearch.subreddit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.redditsearch.core.BaseViewModel
import com.test.redditsearch.core.SubRedditEvent
import com.test.redditsearch.core.UIEvent
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
                        Log.e("test", response.toString())
                        if (subreddits.isEmpty()) {
                            _events.value = SubRedditEvent.OnNoAvailable
                            Log.e("test", response.toString())
                        } else {
                            _events.value = SubRedditEvent.OnFinishedLoading(subreddits)
                            Log.e("test", response.toString())
                        }
                    }
                }.onFailure { error ->
                    _events.value = SubRedditEvent.OnFailedFetching(error.message.toString())
                }
            }
        }
    }
}