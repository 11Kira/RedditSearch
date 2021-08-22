package com.test.redditsearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.redditsearch.core.SubRedditDetailsEvent
import com.test.redditsearch.core.SubRedditEvent
import com.test.redditsearch.core.SubRedditSearchEvent
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
    lateinit var subredditSearchListAdapter: RedditSearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initSearch(binding.searchBox)
        retrieveAllSubreddits()
        observeRetrievedSubreddits()
        observeSearchResults()
        observeRetrievedDetails()
    }

    override fun onBackPressed() {
        if (binding.searchResultList.isVisible) {
            binding.searchResultList.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Submits a request to fetch all available subreddits
     */
    private fun retrieveAllSubreddits() {
        viewModel.retrieveSubreddits()
    }

    /**
     * Submits a query for searching subreddits
     */
    private fun searchSubReddit(searchQuery: String) {
        viewModel.searchSubreddits(searchQuery, "sr")
    }

    /**
     * Initializes the recyclerView
     */
    private fun initRecyclerView(subredditList: List<ApiSubredditResponse>) {
        val baseRedditUrl = "https://www.reddit.com"
        subredditListAdapter = RedditListAdapter(subredditList)
        binding.subredditList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = subredditListAdapter
        }
        subredditListAdapter.onItemClick = { subreddit ->
            val url = baseRedditUrl.plus(subreddit.permalink)
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            })
        }
    }

    /**
     * Initializes the recyclerView
     */
    private fun initSearchRv(subredditList: List<ApiSubredditResponse>) {
        subredditSearchListAdapter = RedditSearchListAdapter(subredditList)
        binding.searchResultList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = subredditSearchListAdapter
        }
        subredditSearchListAdapter.onItemClick = { subredditNamePrefixed ->
            viewModel.getSubRedditDetails(subredditNamePrefixed)
        }
    }

    /**
     * Setup search functionalities
     * @param searchBox The edittext to be used
     */
    private fun initSearch(searchBox: AppCompatEditText) {
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.toString().trim().isNotBlank()) {
                    binding.searchResultList.visibility = View.VISIBLE
                    val txt = s.toString().trim()
                    searchSubReddit(txt)
                } else {
                    binding.searchResultList.visibility = View.GONE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }
        })
    }

    /**
     * Observes the livedata for retrieving all subreddits
     */
    private fun observeRetrievedSubreddits() {
        lifecycleScope.launchWhenCreated {
            viewModel.events.observe(this@MainActivity) { event ->
                when (event) {
                    is SubRedditEvent.OnFinishedLoading -> {
                        initRecyclerView(event.subreddits)
                    }
                }
            }
        }
    }

    /**
     * Observes the search results livedata
     */
    private fun observeSearchResults() {
        lifecycleScope.launchWhenCreated {
            viewModel.events.observe(this@MainActivity) { event ->
                when (event) {
                    is SubRedditSearchEvent.OnFinishedLoadingSearchResults -> {
                        initSearchRv(event.subreddits)
                    }
                }
            }
        }
    }

    /**
     * Observes the livedata for subreddit details
     */
    private fun observeRetrievedDetails() {
        lifecycleScope.launchWhenCreated {
            viewModel.events.observe(this@MainActivity) { event ->
                when (event) {
                    is SubRedditDetailsEvent.OnFinishedLoadingDetails -> {

                    }
                }
            }
        }
    }
}