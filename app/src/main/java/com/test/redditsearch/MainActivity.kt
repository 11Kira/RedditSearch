package com.test.redditsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        retrieveAllSubreddits()
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
    private fun initRecyclerView() {
        binding.repoList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}