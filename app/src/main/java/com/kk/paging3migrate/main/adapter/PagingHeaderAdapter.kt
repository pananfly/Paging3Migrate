package com.kk.paging3migrate.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kk.paging3migrate.R
import com.kk.paging3migrate.databinding.RecyclerPagingHeaderBinding

/**
 * @author kuky.
 * @description
 */

class PagingHeaderAdapter : LoadStateAdapter<PagingHeaderViewHolder>() {
    override fun onBindViewHolder(holder: PagingHeaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState)
            : PagingHeaderViewHolder {
        return PagingHeaderViewHolder.create(
            parent
        )
    }
}


class PagingHeaderViewHolder(private val binding: RecyclerPagingHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.header.setOnClickListener {
            Toast.makeText(binding.root.context, "真听话, 叫你点你就点", Toast.LENGTH_LONG).show()
        }
    }

    fun bind(loadState: LoadState) {
        Log.e("LoadState", loadState.toString())
    }

    companion object {
        fun create(parent: ViewGroup): PagingHeaderViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_paging_header, parent, false
            )
            return PagingHeaderViewHolder(
                RecyclerPagingHeaderBinding.bind(view)
            )
        }
    }
}