package com.test.redditsearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.redditsearch.core.SubRedditDetailsEvent
import com.test.redditsearch.core.response.ApiSubredditResponse
import com.test.redditsearch.databinding.ActivitySubredditBinding
import com.test.redditsearch.subreddit.SubRedditViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity class for displaying the user selected/searched subreddit
 * @author Julius Villagracia
 */
class SubRedditActivity : AppCompatActivity() {

    lateinit var binding: ActivitySubredditBinding
    private val viewModel: SubRedditViewModel by viewModel()
    lateinit var subredditListAdapter: RedditListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subreddit)
        intent.getStringExtra("subreddit")?.let {
            viewModel.getSubRedditDetails(it)
            binding.subRedditName.text = it
        }
        observeRetrievedDetails()
    }

    /**
     * Initializes the recyclerView
     */
    private fun initRecyclerView(subredditList: List<ApiSubredditResponse>) {
        val baseRedditUrl = "https://www.reddit.com"
        subredditListAdapter = RedditListAdapter(subredditList)
        binding.postList.apply {
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
     * Observes the livedata for subreddit details
     */
    private fun observeRetrievedDetails() {
        lifecycleScope.launchWhenCreated {
            viewModel.events.observe(this@SubRedditActivity) { event ->
                when (event) {
                    is SubRedditDetailsEvent.OnFinishedLoadingDetails -> {
                        initRecyclerView(event.subreddits)
                    }
                }
            }
        }
    }
}