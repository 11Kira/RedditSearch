package com.test.redditsearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.redditsearch.core.BaseActivity
import com.test.redditsearch.core.SubRedditEvent
import com.test.redditsearch.core.SubRedditSearchEvent
import com.test.redditsearch.core.response.ApiSubredditResponse
import com.test.redditsearch.databinding.ActivityMainBinding
import com.test.redditsearch.subreddit.SubRedditViewModel
import com.test.redditsearch.utils.NetworkUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main activity class
 * @author Julius Villagracia
 */
class MainActivity : BaseActivity() {

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
        NetworkUtil.checkInternetConnectivity(this) { viewModel.retrieveSubreddits() }
    }

    /**
     * Submits a query for searching subreddits
     * @param searchQuery The search query to be passed to viewModel
     */
    private fun searchSubReddit(searchQuery: String) {
        NetworkUtil.checkInternetConnectivity(this) { viewModel.searchSubreddits(searchQuery, "sr") }
    }

    /**
     * Initializes the recyclerView
     * @param subredditList List of subreddits to be passed to adapter
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
     * Initializes the search result recyclerView
     * @param subredditList List of subreddits to be passed to adapter
     */
    private fun initSearchRv(subredditList: List<ApiSubredditResponse>) {
        subredditSearchListAdapter = RedditSearchListAdapter(subredditList)
        binding.searchResultList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = subredditSearchListAdapter
        }
        subredditSearchListAdapter.onItemClick = { subredditNamePrefixed ->
            startActivity(Intent(
                this@MainActivity,
                SubRedditActivity::class.java
            ).putExtra("subreddit", subredditNamePrefixed))
        }
    }

    /**
     * Setup search functionalities
     * @param searchBox The edittext to be used
     */
    private fun initSearch(searchBox: AppCompatEditText) {
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.toString().trim().isNotBlank() && s.length >= 3) {
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
                    is SubRedditEvent.OnFailedFetching -> {
                        showAlert(
                            "Error",
                            event.error,
                            R.string.ok
                        ) { alertDialog?.dismiss() }
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
                    is SubRedditSearchEvent.OnFailedFetching -> {
                        showAlert(
                            "Error",
                            event.error,
                            R.string.ok
                        ) { alertDialog?.dismiss() }
                    }
                }
            }
        }
    }
}