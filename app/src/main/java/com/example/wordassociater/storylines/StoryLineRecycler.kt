package com.example.wordassociater.storylines

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.StoryLine

class StoryLineRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    enum class Mode { Story, SnippetPart}
    lateinit var storyLineAdapter: StoryLineAdapter
    lateinit var storyLineLiveList: MutableLiveData<List<StoryLine>>
    var horizontal = true
    var mode = Mode.Story

    fun initRecycler(
            liveList: MutableLiveData<List<StoryLine>>,
            onStoryLineSelected: (storyLine: StoryLine) -> Unit,
            onHeaderSelected: (() -> Unit)?,
            orientationHorizontal: Boolean = true,
            mode: Mode = Mode.Story) {
        this.mode = mode
        storyLineAdapter = StoryLineAdapter(onStoryLineSelected, onHeaderSelected)
        layoutManager = LinearLayoutManager(context, if(orientationHorizontal)LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL, false)
        adapter = storyLineAdapter
        this.horizontal = orientationHorizontal
        storyLineLiveList = liveList
        setObserver()
    }

    private fun setObserver() {
        storyLineLiveList.observe(context as LifecycleOwner) {
            val filteredList = if(mode == Mode.Story) it.filter { sl -> sl.type != StoryLine.Type.SnippetPart } else it
            if(horizontal) {
                val header = StoryLine()
                header.isAHeader = true
                if(it != null ) storyLineAdapter.submitList(filteredList + listOf(header))
                else storyLineAdapter.submitList(listOf(header))
                storyLineAdapter.notifyDataSetChanged()
            }
            else {
                storyLineAdapter.submitList(filteredList)
                storyLineAdapter.notifyDataSetChanged()
            }

        }

        DisplayFilter.barColorDark.observe(context as LifecycleOwner) {
            setBackgroundColor(if(it) resources.getColor(R.color.snippets) else resources.getColor(R.color.white))
        }
    }


}