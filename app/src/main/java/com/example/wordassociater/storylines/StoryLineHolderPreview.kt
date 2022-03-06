package com.example.wordassociater.storylines

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderStorylineSimpleBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class StoryLineHolderPreview(val b : HolderStorylineSimpleBinding, private val fromStory: Boolean = false): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item: LiveClass
    override fun onBind(item: LiveClass, takeItemFunc:((item: LiveClass) -> Unit)?) {
        b.storyLineName.text = (item as StoryLine).name
        b.storyLineIcon.setImageResource(item.getIcon())
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