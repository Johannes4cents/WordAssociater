package com.example.wordassociater.timeline

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderEventBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Event
import com.example.wordassociater.popups.Pop

class EventHolderTimeline(val b: HolderEventBinding): RecyclerView.ViewHolder(b.root) {
    lateinit var event: Event

    fun onBind(event: Event) {
        this.event = event
        setContent()
    }

    fun setContent() {
        b.eventName.text = event.content
        b.eventDescription.text = event.description
        b.date.setDateHolder(event.date, event)
        setObserver()
    }

    fun setClickListener() {
        b.btnDelete.setOnClickListener {
            Pop(b.btnDelete.context).confirmationPopUp(b.btnDelete, ::onDeletionConfirmed)
        }

    }

    private fun onDeletionConfirmed(confirmation:Boolean) {
        if(confirmation) {
            event.delete()
        }
    }

    private fun setObserver() {
        DisplayFilter.observeDateShown(b.root.context, b.date)
        DisplayFilter.observeLinesShown(b.root.context, listOf(b.lineBig, b.lineSmall))
    }
}