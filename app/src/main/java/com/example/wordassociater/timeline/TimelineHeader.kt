package com.example.wordassociater.timeline

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HeaderTimelineBinding
import com.example.wordassociater.events.popCreateEvent
import com.example.wordassociater.fire_classes.StoryPart

class TimelineHeader(val b: HeaderTimelineBinding): RecyclerView.ViewHolder(b.root) {
    lateinit var storyPart: StoryPart
    fun onBind(storyPart: StoryPart) {
        this.storyPart = storyPart
        b.root.setOnClickListener {
            popCreateEvent(b.root, storyPart.storyLineList)
        }
    }
}