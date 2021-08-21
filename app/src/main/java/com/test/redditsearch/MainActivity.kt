package com.test.redditsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.redditsearch.core.SubRedditEvent
import com.test.redditsearch.core.response.ApiSubredditResponse
import com.test.redditsearch.databinding.ActivityMainBinding
import com.test.redditsearch.subreddit.SubRedditViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main activity class
 * @author Julius Villagracia
 */
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: SubRedditViewModel by viewModel()
    lateinit var subredditListAdapter: RedditListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        retrieveAllSubreddits()
        observeRetrievedResults()
    }

    /**
     * Submits a request to fetch all available subreddits
     */
    private fun retrieveAllSubreddits() {
        viewModel.retrieveSubreddits()
    }

    /**
     * Initializes the recyclerView
     */
    private fun initRecyclerView(subredditList: List<ApiSubredditResponse>) {
        subredditListAdapter = RedditListAdapter(subredditList)
        binding.repoList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = subredditListAdapter
        }
    }

    /**
     * Observes the search results livedata
     */
    private fun observeRetrievedResults() {
        lifecycleScope.launchWhenCreated {
            viewModel.events.observe(this@MainActivity) { event ->
                when (event) {
                    is SubRedditEvent.OnFinishedLoading -> {
                        initRecyclerView(event.subreddits)
                    }
                    is SubRedditEvent.OnNoAvailable -> {
                        //no available item
                    }
                    is SubRedditEvent.OnFailedFetching -> {
                        Log.e("ERROR", event.error)
                    }
                }
            }
        }
    }
}