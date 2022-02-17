package com.example.wordassociater.snippet_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderSnippetBinding
import com.example.wordassociater.fire_classes.Snippet


class SnippetAdapter: ListAdapter<Snippet, RecyclerView.ViewHolder>(SnippetDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SnippetHolder(HolderSnippetBinding.inflate(LayoutInflater.from(parent.context)), this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val snippet = currentList[position]
        (holder as SnippetHolder).onBind(snippet)
    }
}

class SnippetDiff: DiffUtil.ItemCallback<Snippet>() {
    override fun areItemsTheSame(oldItem: Snippet, newItem: Snippet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Snippet, newItem: Snippet): Boolean {
        return oldItem == newItem
    }
}