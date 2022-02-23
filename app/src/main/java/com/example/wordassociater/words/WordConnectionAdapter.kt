package com.example.wordassociater.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderWordConnectionsBinding

import com.example.wordassociater.utils.WordConnection

class WordConnectionAdapter
    : ListAdapter<WordConnection, RecyclerView.ViewHolder>(WordConnectionDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  WordConnectionHolder(HolderWordConnectionsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val wordConnection = currentList[position]
        (holder as WordConnectionHolder).onBind(wordConnection)
    }
}

class WordConnectionDiff: DiffUtil.ItemCallback<WordConnection>() {
    override fun areItemsTheSame(oldItem: WordConnection, newItem: WordConnection): Boolean {
        return oldItem.word == newItem.word
    }

    override fun areContentsTheSame(oldItem: WordConnection, newItem: WordConnection): Boolean {
        return oldItem == newItem
    }
}