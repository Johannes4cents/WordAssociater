package com.example.wordassociater.storylines

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.StoryLine

class StoryLineRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    lateinit var storyLineAdapter: StoryLineAdapter
    lateinit var storyLineLiveList: MutableLiveData<List<StoryLine>?>

    fun initRecycler(liveList: MutableLiveData<List<StoryLine>?>,
                     onStoryLineSelected: (storyLine: StoryLine) -> Unit,
                     onHeaderSelected: (() -> Unit)?) {
        storyLineAdapter = StoryLineAdapter(onStoryLineSelected, onHeaderSelected)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = storyLineAdapter
        storyLineLiveList = liveList
        setObserver()
    }

    private fun setObserver() {
        storyLineLiveList.observe(context as LifecycleOwner) {
            val header = StoryLine()
            header.isHeader = true
            if(it != null ) storyLineAdapter.submitList(it + listOf(header))
            else storyLineAdapter.submitList(listOf(header))
        }
    }
}