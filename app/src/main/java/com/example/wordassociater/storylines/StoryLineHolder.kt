package com.example.wordassociater.storylines

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderStorylineBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.StoryLine

class StoryLineHolder(
        val b: HolderStorylineBinding,
        val onStoryClicked: (storyLine: StoryLine) -> Unit): RecyclerView.ViewHolder(b.root) {
            lateinit var storyLine: StoryLine
            fun onBind(storyLine: StoryLine) {
                this.storyLine = storyLine
                setClickListener()
                setContent()
                setSelected()
                setObserver()
            }

    private fun setContent() {
        b.storyLineName.text = storyLine.name
        b.storyIcon.setImageResource(storyLine.getIcon())
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onStoryClicked(storyLine)
        }
    }

    private fun setSelected() {
        b.checkbox.setImageResource(if(storyLine.selected) R.drawable.storyline_selected else R.drawable.storyline_unselected)
    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.root.setBackgroundColor(if(it) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(R.color.white))
            b.storyLineName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
        }
    }

}