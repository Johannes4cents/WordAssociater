package com.example.wordassociater.storylines

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderStorylineSimpleBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.StoryLine

class SimpleStoryLineHolder(val b : HolderStorylineSimpleBinding,private val fromStory: Boolean = false): RecyclerView.ViewHolder(b.root) {
    fun onBind(storyLine: StoryLine) {
        b.storyLineName.text = storyLine.name
        b.storyLineIcon.setImageResource(storyLine.getIcon())
        setObserver()
    }

    private fun setObserver() {
        if(fromStory) {
            DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
                b.storyLineName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black) )
            }
        }
    }
}