package com.kk.paging3migrate.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kk.paging3migrate.R
import com.kk.paging3migrate.databinding.RecyclerArticleItemBinding
import com.kk.paging3migrate.entity.DataX

/**
 * @author kuky.
 * @description
 */
class ArticleAdapter : PagingDataAdapter<DataX, ArticleViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.binding.article = getItem(position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataX>() {
            override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ArticleViewHolder(val binding: RecyclerArticleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): ArticleViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_article_item, parent, false
            )
            val binding = RecyclerArticleItemBinding.bind(view)
            return ArticleViewHolder(binding)
        }
    }
}