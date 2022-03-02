package com.example.wordassociater.storylines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderStorylineSimpleBinding
import com.example.wordassociater.fire_classes.StoryLine

class SimpleStoryLineAdapter(val fromStory: Boolean = false): ListAdapter<StoryLine, RecyclerView.ViewHolder>(StoryLineDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SimpleStoryLineHolder(HolderStorylineSimpleBinding.inflate(LayoutInflater.from(parent.context)), fromStory)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SimpleStoryLineHolder).onBind(getItem(position))
    }

}