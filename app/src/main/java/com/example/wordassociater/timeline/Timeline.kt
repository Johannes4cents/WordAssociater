package com.example.wordassociater.timeline

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.wordassociater.databinding.TimelineBinding

class Timeline(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {

    val b = TimelineBinding.inflate(LayoutInflater.from(context), this, true)

    fun initTimeline() {

    }
}