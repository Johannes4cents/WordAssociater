package com.example.wordassociater.storylines

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderStorylineHeaderBinding

class StoryLineHeader(
        val b: HolderStorylineHeaderBinding,
        private val onHeaderClicked: (() -> Unit)?): RecyclerView.ViewHolder(b.root) {
    fun onBind() {
        setClickListener()
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            if(onHeaderClicked != null) {
                onHeaderClicked!!()
            }
        }
    }

}
