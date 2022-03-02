package com.example.wordassociater.timeline

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.Main
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.StoryPart

class TimelineRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    private var liveStoryLines: MutableLiveData<List<StoryLine>>? = null
    private var selectedWords: MutableLiveData<List<Word>>? = null
    private var selectedCharacters: MutableLiveData<List<Character>>? = null
    private var whatParts: MutableLiveData<List<StoryPart.Type>>? = null
    lateinit var timelineAdapter: TimelineAdapter
    lateinit var onStoryPartsDisplayed: (list: List<StoryPart>) -> Unit


    fun initRecycler(
            liveStoryLines: MutableLiveData<List<StoryLine>>? = null,
            selectedWords: MutableLiveData<List<Word>>? = null,
            selectedCharacters: MutableLiveData<List<Character>>? = null,
            whatParts: MutableLiveData<List<StoryPart.Type>>? = null,
            onStoryPartsShown: (list: List<StoryPart>) -> Unit,
            onSnippetSelected: (snippet : Snippet) -> Unit) {
        this.liveStoryLines = liveStoryLines
        this.selectedWords = selectedWords
        this.selectedCharacters = selectedCharacters
        this.whatParts = whatParts
        this.timelineAdapter = TimelineAdapter(onSnippetSelected)
        this.onStoryPartsDisplayed = onStoryPartsShown
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = timelineAdapter
        setObserver()
    }

    fun setObserver() {
        liveStoryLines?.let { makeObserver(it) }
        selectedWords?.let { makeObserver(it) }
        selectedCharacters?.let { makeObserver(it) }
        whatParts?.let { makeObserver(it) }

        Main.eventList.observe(context as LifecycleOwner) {
            filterAndSubmitList()
        }

        Main.snippetList.observe(context as LifecycleOwner) {
            filterAndSubmitList()
        }

        Main.proseList.observe(context as LifecycleOwner) {
            filterAndSubmitList()
        }
    }

    private fun <T>makeObserver(liveData: MutableLiveData<T>) {
        liveData.observe(context as LifecycleOwner) {
            filterAndSubmitList()
        }
    }

    private fun filterAndSubmitList() {
        var storyParts = filteredStoryParts()
        timelineAdapter.submitList(getHeaderList(storyParts))
        onStoryPartsDisplayed(storyParts)
    }

    private fun getHeaderList(list: List<StoryPart>): List<StoryPart> {
        var submitList = list.sortedBy { sp -> sp.date.day }.sortedBy { sp -> sp.date.month }.sortedBy { sp -> sp.date.year }
        val header = StoryPart(id = 0, "", mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), Date(), StoryPart.Type.Header)
        header.isStoryPartHeader = true

        //check if at least 1 storyLine is selected
        val activeStoryLines = mutableListOf<StoryLine>()
        for(sl in liveStoryLines!!.value!!) {
            if(sl.selected) activeStoryLines.add(sl)
        }
        // add the storyLines to the Header for when creating an Event
        header.storyLineList = StoryLine.getIdList(activeStoryLines).toMutableList()
        if(activeStoryLines.isNotEmpty()) {
            submitList = listOf(header) + submitList
        }
        return submitList
    }

    private fun getAllStoryParts(): List<StoryPart> {
        return (Main.snippetList.value!! + Main.proseList.value!!+ Main.eventList.value!!).toList()
    }

    private fun modifyStoryPart(storyPart: StoryPart) {
        val anyChar = Character(id = 22, name = "Any", connectId = 0)
        anyChar.selected = true
        val anyWord = Word(id = 0, text = "Any")
        anyWord.selected = true

        if(!storyPart.characterList.contains(anyChar.id)) storyPart.characterList.add(anyChar.id)
        if(!storyPart.wordList.contains(anyWord.id)) storyPart.wordList.add(anyWord.id)
    }

    private fun filteredStoryParts(): List<StoryPart> {
        var qualifiedParts = getAllStoryParts().toMutableList()
        for(sp in qualifiedParts) {
            modifyStoryPart(sp)
        }

        if(liveStoryLines != null) {
            val parts = qualifiedParts.toList()
            for(sp in parts) {
                var markedForRemoval = true
                for(storyLine in liveStoryLines!!.value!!) {
                    if(storyLine.selected && sp.storyLineList.contains(storyLine.id)) {
                        markedForRemoval = false
                        break
                    }
                }
                if(markedForRemoval) qualifiedParts.remove(sp)
            }
        }

        if(selectedCharacters != null) {
            var parts = qualifiedParts.toList()
            for(sp in parts) {
                var markedForRemoval = true
                for(character in selectedCharacters!!.value!!) {
                    if(character.selected && sp.characterList.contains(character.id)) {
                        markedForRemoval = false
                        break
                    }
                }
                if(markedForRemoval) qualifiedParts.remove(sp)
            }
        }

        if(selectedWords != null) {
            val parts = qualifiedParts.toList()
            for(sp in parts) {
                var markedForRemoval = true
                for(word in selectedWords!!.value!!) {
                    if(word.selected && sp.wordList.contains(word.id)) {
                        Log.i("filterProb", "selected Word is ${word.text}")
                        markedForRemoval = false
                        break
                    }
                }
                if(markedForRemoval) qualifiedParts.remove(sp)
            }
        }

        if(whatParts != null) {
            val parts = qualifiedParts.toList()
            for(sp in parts) {
                var markedForRemoval = true
                for(type in whatParts!!.value!!) {
                    if(sp.type == type) {
                        markedForRemoval = false
                        break
                    }
                }
                if(markedForRemoval) qualifiedParts.remove(sp)
            }
        }

        return qualifiedParts.sortedBy { sp -> sp.date.day }.sortedBy { sp -> sp.date.month }.sortedBy { sp -> sp.date.year }
    }

}