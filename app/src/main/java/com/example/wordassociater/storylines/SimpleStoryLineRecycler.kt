package com.example.wordassociater.storylines

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.StoryLine

class SimpleStoryLineRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    lateinit var liveList : MutableLiveData<List<StoryLine>>
    lateinit var simpleAdapter: SimpleStoryLineAdapter

    fun initRecycler(liveList: MutableLiveData<List<StoryLine>>, fromStory: Boolean = false) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        simpleAdapter = SimpleStoryLineAdapter(fromStory)
        adapter = simpleAdapter

        liveList.observe(context as LifecycleOwner) {
            simpleAdapter.submitList(it.sortedBy { sl -> sl.snippetsList.count() })
        }
    }
}