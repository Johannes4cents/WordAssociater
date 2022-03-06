package com.example.wordassociater.storylines

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderStorylineBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class StoryLineHolderList(
        val b: HolderStorylineBinding): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item: LiveClass
    var onStoryClicked: ((storyLine: LiveClass) -> Unit)? = null
    override fun onBind(item: LiveClass, takeItemFunc:((item: LiveClass) -> Unit)?) {
        this.item = item
        this.onStoryClicked = takeItemFunc
        setClickListener()
        setContent()
        setSelected()
        setObserver()
    }

    private fun setContent() {
        b.storyLineName.text = (item as StoryLine).name
        b.storyIcon.setImageResource((item as StoryLine).getIcon())
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onStoryClicked!!(item)
        }
    }

    private fun setSelected() {
        b.checkbox.setImageResource(if(item.selected) R.drawable.storyline_selected else R.drawable.storyline_unselected)
    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.root.setBackgroundColor(if(it) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(R.color.white))
            b.storyLineName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
        }
    }



}