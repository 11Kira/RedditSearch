package com.test.redditsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.redditsearch.core.response.ApiSubredditResponse
import com.test.redditsearch.databinding.ListItemSubredditBinding
import com.test.redditsearch.subreddit.Subreddit

/**
 * Adapter class for Reddit list items
 * @author Julius Villagracia
 */
class RedditListAdapter(
    private var subredditList: List<ApiSubredditResponse>
) : RecyclerView.Adapter<RedditListAdapter.ViewHolder>() {

    lateinit var binding: ListItemSubredditBinding
    var onItemClick: ((Subreddit) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (itemCount > 0) {
            val subredditResponse = subredditList[position]
            holder.apply {
                binding.titleTxt.text = subredditResponse.data.title
                binding.subredditName.text = subredditResponse.data.subredditNamePrefixed
                binding.authorTxt.text = subredditResponse.data.author
            }
        }
    }

    override fun getItemCount(): Int {
        return subredditList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_subreddit,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(
        val binding: ListItemSubredditBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                //onItemClick?.invoke(subredditList[bindingAdapterPosition])
            }
        }
    }
}