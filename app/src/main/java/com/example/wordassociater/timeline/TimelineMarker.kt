package com.example.wordassociater.timeline

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.wordassociater.R
import com.example.wordassociater.databinding.TimelineMarkerBinding
import com.example.wordassociater.fire_classes.Event
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.StoryPart

class TimelineMarker(context: Context, attributeSet: AttributeSet?, val storyPart: StoryPart, val scrollToFunc: (index: Int) -> Unit): LinearLayout(context, attributeSet) {
    val b = TimelineMarkerBinding.inflate(LayoutInflater.from(context), this, true)
    var scrolltToIndex = 0

    init {
        when(storyPart) {
            is Snippet -> {
                b.storyPartLine.visibility = View.VISIBLE
                b.eventImage.visibility = View.GONE
                b.storyPartLine.setBackgroundColor(b.root.context.resources.getColor(R.color.green))
            }
            is Event -> {
                b.eventImage.setImageResource(storyPart.getImage().getDrawable())
            }

        }

        setOnClickListener {
            scrollToFunc(scrolltToIndex)
        }
    }

}