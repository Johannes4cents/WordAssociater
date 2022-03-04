package com.example.wordassociater.timeline

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
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
        b.eventName.text = event.name
        b.eventDescription.text = event.description
        b.date.setDateHolder(event.date, event)
        setObserver()
        setClickListener()
    }

    fun setClickListener() {
        b.btnDelete.setOnClickListener {
            Log.i("eventProb", "deleteButtonClicked")
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

        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.root.setBackgroundColor(if(it) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(R.color.white))
            b.eventName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
        }
    }
}