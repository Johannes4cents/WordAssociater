package com.example.wordassociater.timeline

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.TimelineBinding
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
    }

    private fun onStoryPartsShown(storyParts: List<StoryPart>) {

    }
}