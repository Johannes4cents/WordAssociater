package com.example.wordassociater.timeline

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HeaderTimelineBinding
import com.example.wordassociater.databinding.HolderEventBinding
import com.example.wordassociater.databinding.HolderProseBinding
import com.example.wordassociater.databinding.HolderSnippetBinding
import com.example.wordassociater.fire_classes.Event
import com.example.wordassociater.fire_classes.Prose
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.StoryPart

class TimelineAdapter(val onSnippetSelected: (snippet : Snippet) -> Unit): ListAdapter<StoryPart, RecyclerView.ViewHolder>(StoryPartDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val snippetHolder = SnippetHolderTimeline(HolderSnippetBinding.inflate(LayoutInflater.from(parent.context)), onSnippetSelected)
        val proseHolder = ProseHolderTimeline(HolderProseBinding.inflate(LayoutInflater.from(parent.context)))
        val eventHolder = EventHolderTimeline(HolderEventBinding.inflate(LayoutInflater.from(parent.context)))
        val header = TimelineHeader(HeaderTimelineBinding.inflate(LayoutInflater.from(parent.context)))

        return when(viewType) {
            1 -> snippetHolder
            2 -> eventHolder
            4 -> proseHolder
            5 -> header
            else -> header
        }
    }

    override fun getItemViewType(position: Int): Int {
        val storyPart = getItem(position)
        var typeNumber = when(storyPart.type) {
            StoryPart.Type.Snippet -> 1
            StoryPart.Type.Event -> 2
            StoryPart.Type.Prose -> 4
            StoryPart.Type.Header -> 5
        }
        if(storyPart.isStoryPartHeader) typeNumber = 5
        Log.i("filterProb", "storyPart is ${storyPart.content}")
        return typeNumber
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val storyPart = getItem(position)
        when(storyPart.type) {
            StoryPart.Type.Snippet -> {
                (storyPart as Snippet)
                (holder as SnippetHolderTimeline).onBind(storyPart)
            }
            StoryPart.Type.Event -> {
                storyPart as Event
                (holder as EventHolderTimeline).onBind(storyPart)
            }
            StoryPart.Type.Prose -> (storyPart as Prose)
        }

        if(holder is TimelineHeader) {
            storyPart as StoryPart
            holder.onBind(storyPart)
        }


    }
}

class StoryPartDiff: DiffUtil.ItemCallback<StoryPart>() {
    override fun areItemsTheSame(oldItem: StoryPart, newItem: StoryPart): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StoryPart, newItem: StoryPart): Boolean {
        return (
                        oldItem.id == newItem.id &&
                        oldItem.content == newItem.content &&
                        oldItem.characterList == newItem.characterList &&
                        oldItem.date == newItem.date &&
                        oldItem.wordList == newItem.wordList &&
                        oldItem.storyLineList == newItem.storyLineList &&
                        oldItem.date == newItem.date
                )
    }

}