package com.test.redditsearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
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
            val subreddit = subredditList[position].data
            val author = "Posted by: "
            holder.apply {
                binding.titleTxt.text = subreddit.title
                binding.subredditName.text = subreddit.subredditNamePrefixed
                binding.authorTxt.text = author.plus(subreddit.author)
                if (subreddit.imageUrl?.isNotBlank() == true) {
                    binding.thumbImg.visibility = View.VISIBLE
                    binding.thumbImg.load(subreddit.imageUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_launcher_background)
                    }
                }
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
                onItemClick?.invoke(subredditList[bindingAdapterPosition].data)
            }
        }
    }
}