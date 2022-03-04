package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarSnippetPartsBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.StoryPart
import com.example.wordassociater.popups.popCharacterSelector

class SnippetPartsBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarSnippetPartsBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var storyPart: StoryPart

    fun initSnippetsBar() {

        setObserver()
    }

    fun setTheSnippet(snippet: Snippet) {
        this.storyPart = snippet
    }

    fun setLocationsPopup() {
        b.btnLocation.setOnClickListener {

        }
    }

    fun setEventsPopup() {
        b.btnEvents.setOnClickListener {

        }
    }

    fun setItemsPopup() {
        b.btnItems.setOnClickListener {

        }
    }

    fun setCharacterPopup() {
        b.btnCharacter.setOnClickListener {
            popCharacterSelector(b.btnCharacter, storyPart.liveCharacter, ::onCharacterSelected)
        }
    }

    private fun onCharacterSelected(character: Character) {
        storyPart.takeCharacter(character)
    }

    fun setEventsFunc(func : () -> Unit) {
        b.btnEvents.setOnClickListener {
            func()
        }
    }

    fun setItemsFunc(func : () -> Unit) {
        b.btnItems.setOnClickListener {
            func()
        }
    }

    fun setCharacterFunc(func : () -> Unit) {
        b.btnCharacter.setOnClickListener {
            func()
        }
    }

    fun setLocationsFunc(func : () -> Unit) {
        b.btnLocation.setOnClickListener {
            func()
        }
    }

    private fun setObserver() {
        DisplayFilter.observeBarColorDark(context, b.root)
        setColorOnStartup()
    }

    private fun setColorOnStartup() {
        val darkBar = DisplayFilter.barColorDark.value!!
        b.root.setBackgroundColor(if(darkBar) context.resources.getColor(R.color.snippets) else context.resources.getColor(R.color.white))
    }

}