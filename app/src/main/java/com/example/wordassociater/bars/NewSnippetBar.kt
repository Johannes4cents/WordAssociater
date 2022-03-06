package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.wordassociater.databinding.BarNewSnippetBinding
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.popups.popLiveClass
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.LiveClass
import com.example.wordassociater.words.WordLinear

class NewSnippetBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarNewSnippetBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var previewBar: PreviewBar

    companion object {
        var newSnippet = Snippet(id = FireStats.getStoryPartId())
    }


    lateinit var navController: NavController

    init {
        setClickListener()
        setObserver()
        setInputField()
        setPreviewBar()
    }

    private fun setInputField() {
        b.snippetInput.disableOutSideEditClickCheck()
    }

    fun takePreviewBar(previewBar: PreviewBar) {
       this.previewBar = previewBar
    }

    fun setPreviewBar() {
        if(::previewBar.isInitialized) {
            Log.i("previewBar", "previewBar init ")
            previewBar.initBar(newSnippet, useFilter = false)
        }
    }

    private fun setClickListener() {
        b.btnAddCharacter.setOnClickListener {
            popLiveClass(LiveRecycler.Type.Character, b.btnAddCharacter, newSnippet.liveCharacter, ::onCharacterSelected, fromMiddle = true)
            newSnippet.getFullCharacterList()
        }

        b.btnItems.setOnClickListener {
            popLiveClass(LiveRecycler.Type.Item, b.btnItems, newSnippet.liveItems, ::onItemSelected, fromMiddle = true)
            newSnippet.getFullItemList()
        }

        b.btnEvent.setOnClickListener {
            popLiveClass(LiveRecycler.Type.Event, b.btnEvent, newSnippet.liveEvents, ::onEventSelected, fromMiddle = true)
            newSnippet.getFullEventList()
        }

        b.btnLocation.setOnClickListener {
            popLiveClass(LiveRecycler.Type.Location, b.btnLocation, newSnippet.liveLocations, ::onLocationSelected, fromMiddle = true)
            newSnippet.getFullLocationList()
        }

        b.btnStoryLines.setOnClickListener {
            popLiveClass(LiveRecycler.Type.StoryLine, b.btnStoryLines, newSnippet.liveSelectedStoryLines, ::onStoryLineSelected, fromMiddle = true)
            newSnippet.getFullStoryLineList()
        }

        b.btnBack.setOnClickListener {
            closeSnippetInput()
            AddStuffBar.snippetInputOpen.value = false
        }

        b.snippetInput.setOnEnterFunc {
            saveSnippet()
            AddStuffBar.snippetInputOpen.value = false
            Helper.getIMM(context).hideSoftInputFromWindow(b.snippetInput.windowToken, 0)
        }
    }

    private fun onCharacterSelected(character: LiveClass) {
        newSnippet.takeCharacter(character as Character)
    }

    private fun onItemSelected(item: LiveClass) {
        newSnippet.takeItem(item as Item)
    }

    private fun onEventSelected(event: LiveClass) {
        newSnippet.takeEvent(event as Event)
    }

    private fun onLocationSelected(location: LiveClass) {
        newSnippet.takeLocation(location as Location)
    }

    private fun onStoryLineSelected(storyLine: LiveClass) {
        newSnippet.takeStoryLine(storyLine as StoryLine)
    }

    private fun setObserver() {
        AddStuffBar.snippetInputOpen.observe(context as LifecycleOwner) {
            if(it == true) {
                b.snippetInput.visibility = View.VISIBLE
            }
            else {
                closeSnippetInput()
            }
        }
    }

    private fun closeSnippetInput() {
        b.snippetInput.resetField()
        newSnippet = Snippet(id = FireStats.getStoryPartId())
        Helper.getIMM(context).hideSoftInputFromWindow(b.snippetInput.windowToken, 0)
        setPreviewBar()
    }

    fun saveSnippet() {
        if(b.snippetInput.content.isNotEmpty()) {
            for(word in WordLinear.selectedWords) {
                newSnippet.wordList.add(word.id)
                word.snippetsList.add(newSnippet.id)
                FireWords.update(word.id, "snippetsList", word.snippetsList)
                word.increaseWordUsed()
            }
            newSnippet.content = b.snippetInput.content
            WordConnection.connect(newSnippet)
            newSnippet.handleSnippetPartsForSave()
            FireSnippets.add(newSnippet, context)
            closeSnippetInput()
        }
        else {

            closeSnippetInput()
        }
        WordLinear.deselectWords()
    }

}