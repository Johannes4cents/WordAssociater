package com.example.wordassociater.timeline

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.TimelineBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.StoryPart

class Timeline(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = TimelineBinding.inflate(LayoutInflater.from(context), this, true)

    fun initTimeline(liveStoryLines: MutableLiveData<List<StoryLine>>? = null,
                     selectedWords: MutableLiveData<List<Word>>? = null,
                     selectedCharacters: MutableLiveData<List<Character>>? = null,
                     whatParts: MutableLiveData<List<StoryPart.Type>>? = null,
                     onSnippetSelected: (snippet : Snippet) -> Unit
                    ) {
        b.timelineRecycler.initRecycler(liveStoryLines, selectedWords, selectedCharacters, whatParts, ::onStoryPartsShown, onSnippetSelected)
        setObserver()
    }


    private fun onStoryPartsShown(storyParts: List<StoryPart>) {
        b.startLinear.removeAllViews()
        b.endLinear.removeAllViews()
        b.centerLinear.removeAllViews()

        if(storyParts.isNotEmpty()) {
            val parts = setStartAndEnd(storyParts.toMutableList())

            var index = 2
            for(part in parts) {
                val marker = TimelineMarker(context, null, part, ::scrollTimeLineFunc)
                val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
                params.weight = 1f
                marker.layoutParams = params
                marker.scrolltToIndex = index
                b.centerLinear.addView(marker)
                index ++
            }
        }

        Log.i("timeLineProb", "b.startLinear.childCount ${b.startLinear.childCount}")
    }

    private fun scrollTimeLineFunc(index: Int) {
        b.timelineRecycler.smoothScrollToPosition(index)
    }

    private fun setStartAndEnd(storyParts: MutableList<StoryPart>): List<StoryPart> {
//        val firstStory = storyParts.removeFirst()
//        val markerStart = TimelineMarker(context, null, firstStory)
//        b.startLinear.addView(markerStart)

        if(storyParts.isNotEmpty()) {
            val lastStory = storyParts.removeLast()
            val markerEnd = TimelineMarker(context, null, lastStory, ::scrollTimeLineFunc)
            b.endLinear.addView(markerEnd)
        }
        return storyParts
    }


    private fun setObserver() {
        DisplayFilter.barColorDark.observe(context as LifecycleOwner) {
            b.root.setBackgroundColor(if(it) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(R.color.white))
        }
    }
}