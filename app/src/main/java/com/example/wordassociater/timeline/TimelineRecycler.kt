package com.example.wordassociater.timeline

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.Main
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.StoryPart

class TimelineRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    private var liveStoryLines: MutableLiveData<List<StoryLine>>? = null
    private var selectedWords: MutableLiveData<List<Word>>? = null
    private var selectedCharacters: MutableLiveData<List<Character>>? = null
    private var whatParts: MutableLiveData<List<StoryPart.Type>>? = null
    lateinit var timelineAdapter: TimelineAdapter


    fun initRecycler(
            liveStoryLines: MutableLiveData<List<StoryLine>>? = null,
            selectedWords: MutableLiveData<List<Word>>? = null,
            selectedCharacters: MutableLiveData<List<Character>>? = null,
            whatParts: MutableLiveData<List<StoryPart.Type>>? = null) {
        this.liveStoryLines = liveStoryLines
        this.selectedWords = selectedWords
        this.selectedCharacters = selectedCharacters
        this.whatParts = whatParts
        this.timelineAdapter = TimelineAdapter()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setObserver()
    }

    fun setObserver() {
        liveStoryLines?.let { makeObserver(it) }
        selectedWords?.let { makeObserver(it) }
        selectedCharacters?.let { makeObserver(it) }
        whatParts?.let { makeObserver(it) }
    }

    private fun <T>makeObserver(liveData: MutableLiveData<T>) {
        liveData.observe(context as LifecycleOwner) {
            timelineAdapter.submitList(filteredStoryParts())
        }
    }

    private fun getAllStoryParts(): List<StoryPart> {
        return Main.snippetList.value!! + Main.dialogueList.value!! + Main.proseList.value!!+ Main.eventList.value!!
    }

    private fun filteredStoryParts(): List<StoryPart> {
        var qualifiedParts = getAllStoryParts().toMutableList()

        if(liveStoryLines != null) {
            val parts = mutableListOf<StoryPart>()
            for(sp in qualifiedParts) {
                for(storyLine in liveStoryLines!!.value!!) {
                    if(sp.storyLineList.contains(storyLine.id)) parts.add(sp)
                }
            }
            qualifiedParts = parts
        }

        if(selectedCharacters != null) {
            var parts = mutableListOf<StoryPart>()
            for(sp in qualifiedParts) {
                for(character in selectedCharacters!!.value!!) {
                    if(sp.characterList.contains(character.id)) parts.add(sp)
                }
            }
            qualifiedParts.plusAssign(parts)
        }

        if(selectedWords != null) {
            val parts = mutableListOf<StoryPart>()
            for(sp in qualifiedParts) {
                for(word in selectedWords!!.value!!) {
                    if(sp.wordList.contains(word.id)) parts.add(sp)
                }
            }
            qualifiedParts.plusAssign(parts)
        }

        if(whatParts != null) {
            val parts = qualifiedParts
            for(sp in qualifiedParts) {
                for(type in whatParts!!.value!!) {
                    if(sp.type == type) parts.add(sp)
                }
            }
            qualifiedParts.plusAssign(parts)
        }


        return qualifiedParts
    }
}