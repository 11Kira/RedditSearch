package com.test.redditsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.redditsearch.core.response.ApiSubredditResponse
import com.test.redditsearch.databinding.ListItemSearchResultBinding
import com.test.redditsearch.subreddit.Subreddit

class RedditSearchListAdapter (
    private var subredditList: List<ApiSubredditResponse>
) : RecyclerView.Adapter<RedditSearchListAdapter.ViewHolder>() {

    lateinit var binding: ListItemSearchResultBinding
    var onItemClick: ((Subreddit) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (itemCount > 0) {
            val subreddit = subredditList[position].data
            holder.apply {
                binding.resultName.text = subreddit.displayNamePrefixed
            }
        }
    }

    override fun getItemCount(): Int {
        return subredditList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_search_result,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(
        val binding: ListItemSearchResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(subredditList[bindingAdapterPosition].data)
            }
        }
    }
}