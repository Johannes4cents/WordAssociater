package com.example.wordassociater.storylines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderStorylineBinding
import com.example.wordassociater.databinding.HolderStorylineHeaderBinding
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.utils.LiveClass

class StoryLineAdapter(
        private val onStorySelected: (storyLine: StoryLine) -> Unit,
        private val onHeaderSelected: (() -> Unit)?)
    : ListAdapter<StoryLine, RecyclerView.ViewHolder>(StoryLineDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = StoryLineHolderList(HolderStorylineBinding.inflate(LayoutInflater.from(parent.context)))
        val header = StoryLineHeader(HolderStorylineHeaderBinding.inflate(LayoutInflater.from(parent.context)), onHeaderSelected)
        return when(viewType) {
            1 -> holder
            else -> header
        }

    }

    override fun getItemViewType(position: Int): Int {
        val storyLine = getItem(position)
        return when(storyLine.isAHeader) {
            false -> 1
            true -> 2
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        fun onStoryPicked(storyLine: LiveClass) {
            onStorySelected(storyLine as StoryLine)
        }

        val storyLine = getItem(position)
        if(storyLine.isAHeader) (holder as StoryLineHeader).onBind()
        else (holder as StoryLineHolderList).onBind(storyLine, ::onStoryPicked)
    }
}

class StoryLineDiff: DiffUtil.ItemCallback<StoryLine>() {
    override fun areItemsTheSame(oldItem: StoryLine, newItem: StoryLine): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StoryLine, newItem: StoryLine): Boolean {
        return oldItem == newItem
    }

}