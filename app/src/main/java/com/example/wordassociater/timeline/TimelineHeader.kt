package com.example.wordassociater.timeline

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HeaderTimelineBinding
import com.example.wordassociater.events.popCreateEvent

class TimelineHeader(val b: HeaderTimelineBinding): RecyclerView.ViewHolder(b.root) {

    fun onBind() {
        b.root.setOnClickListener {
            popCreateEvent()
        }
    }
}