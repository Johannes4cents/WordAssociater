package com.example.wordassociater.timeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.*
import com.example.wordassociater.fire_classes.Dialogue
import com.example.wordassociater.fire_classes.Prose
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Event
import com.example.wordassociater.utils.StoryPart

class TimelineAdapter: ListAdapter<StoryPart, RecyclerView.ViewHolder>(StoryPartDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val snippetHolder = SnippetHolderTimeline(HolderSnippetBinding.inflate(LayoutInflater.from(parent.context)))
        val proseHolder = ProseHolderTimeline(HolderProseBinding.inflate(LayoutInflater.from(parent.context)))
        val eventHolder = EventHolderTimeline(HolderEventBinding.inflate(LayoutInflater.from(parent.context)))
        val dialogueHolder = DialogueHolderTimeline(HolderDialogueBinding.inflate(LayoutInflater.from(parent.context)))
        val header = TimelineHeader(HeaderTimelineBinding.inflate(LayoutInflater.from(parent.context)))

        return when(viewType) {
            1 -> snippetHolder
            2 -> eventHolder
            3 -> dialogueHolder
            4 -> proseHolder
            else -> header
        }
    }

    override fun getItemViewType(position: Int): Int {
        val storyPart = getItem(position)
        var typeNumber = when(storyPart.type) {
            StoryPart.Type.Snippet -> 1
            StoryPart.Type.Event -> 2
            StoryPart.Type.Dialogue -> 3
            StoryPart.Type.Prose -> 4
        }
        if(storyPart.isStoryPartHeader) typeNumber = 5

        return typeNumber
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val storyPart = getItem(position)
        when(storyPart.type) {
            StoryPart.Type.Snippet -> (storyPart as Snippet)
            StoryPart.Type.Event -> (storyPart as Event)
            StoryPart.Type.Dialogue -> (storyPart as Dialogue)
            StoryPart.Type.Prose -> (storyPart as Prose)
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
                        oldItem.storyLineList == newItem.storyLineList
                )
    }

}