package com.test.redditsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.redditsearch.databinding.ListItemSubredditBinding

/**
 * Adapter class for Reddit list items
 * @author Julius Villagracia
 */
class RedditListAdapter(
    private var subredditList: List<Subreddit>
) : RecyclerView.Adapter<RedditListAdapter.ViewHolder>() {

    lateinit var binding: ListItemSubredditBinding
    var onItemClick: ((Subreddit) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (itemCount > 0) {
            val subreddit = subredditList[position]
            holder.apply {
                binding.repoNameTxt.text = subreddit.fullName
                binding.descTxt.text = subreddit.description
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
                onItemClick?.invoke(subredditList[bindingAdapterPosition])
            }
        }
    }
}