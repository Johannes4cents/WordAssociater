package com.example.wordassociater.storylines

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderStorylineBinding
import com.example.wordassociater.fire_classes.StoryLine

class StoryLineHolder(
        val b: HolderStorylineBinding,
        val onStoryClicked: (storyLine: StoryLine) -> Unit): RecyclerView.ViewHolder(b.root) {
    companion object {
        var selectedHolder = MutableLiveData<StoryLineHolder>()
    }
            lateinit var storyLine: StoryLine
            fun onBind(storyLine: StoryLine) {
                this.storyLine = storyLine
                setClickListener()
                setContent()
                setObserver()
            }

    private fun setContent() {
        b.storyLineName.text = storyLine.name
        b.storyIcon.setImageResource(storyLine.getIcon())
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            selectedHolder.value = this
            onStoryClicked(storyLine)
        }
    }

    private fun setObserver() {
        selectedHolder.observe(b.root.context as LifecycleOwner) {
            b.storyLineName.setBackgroundColor(if(this == it) b.root.resources.getColor(R.color.lightYellow) else b.root.resources.getColor(R.color.white))
        }
    }

}