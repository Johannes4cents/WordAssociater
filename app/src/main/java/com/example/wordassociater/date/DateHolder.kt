package com.example.wordassociater.date

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.wordassociater.databinding.DateBinding
import com.example.wordassociater.fire_classes.Event
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.firestore.FireEvents
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.StoryPart

class DateHolder(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    private val b = DateBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var date: Date
    private lateinit var storyPart: StoryPart
    private var newEvent = false
    var popFromView: View = b.root

    init {
    }

    fun setDateHolder(date:Date, storyPart: StoryPart, newEvent: Boolean = false, popFromView: View? = null) {
        this.date = date
        this.storyPart = storyPart
        if(popFromView != null) this.popFromView = popFromView
        setClickListener()
        setContent()
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            popDateSelector(popFromView, date, ::onDateSelected)
        }
    }

    private fun setContent() {
        Log.i("dateProb", "inSetContent of Date year is ${date.year}")
        if(date.year < 1800) {
            b.dateLinear.visibility = View.GONE
            b.timeLineIcon.visibility = View.VISIBLE
        }
        else {
            b.dateLinear.visibility = View.VISIBLE
            b.timeLineIcon.visibility = View.GONE
        }
        b.textDay.text = date.day.toString()
        b.textMonth.text = date.month
        b.textYear.text = date.year.toString()
    }

    private fun onDateSelected(date: Date) {
        this.date = date
        setContent()
        when(storyPart) {
            is Snippet -> FireSnippets.update(storyPart.id, "date", date)
            is Event -> {
                if(!newEvent) FireEvents.update(storyPart.id, "date", date)
            }
        }

    }
}