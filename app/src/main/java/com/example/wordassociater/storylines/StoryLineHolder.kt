package com.example.wordassociater.storylines

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderStorylinePopupBinding
import com.example.wordassociater.fire_classes.StoryLine

class StoryLineHolder(
        val b: HolderStorylinePopupBinding,
        val onStoryClicked: (storyLine: StoryLine) -> Unit): RecyclerView.ViewHolder(b.root) {
            lateinit var storyLine: StoryLine
            fun onBind(storyLine: StoryLine) {
                this.storyLine = storyLine
                setClickListener()
                setContent()
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

}