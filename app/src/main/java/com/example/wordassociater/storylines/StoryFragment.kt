package com.example.wordassociater.storylines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentStoryBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.popups.popCreateStoryLine

class StoryFragment: Fragment() {
    lateinit var b : FragmentStoryBinding
    private val selectedStoryLines = MutableLiveData<List<StoryLine>>(mutableListOf())
    private var allSelected = false

    companion object {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentStoryBinding.inflate(inflater)
        setContent()
        setObserver()
        setClickListener()
        return b.root
    }

    private fun setContent() {
        selectedStoryLines.value = Main.storyLineList.value!!.toList()
        b.storyLineRecycler.initRecycler(selectedStoryLines, ::onStoryLineClicked, ::onHeaderClicked)
        b.timelineRecycler.initTimeline(selectedStoryLines, b.storyBar.selectedWords, b.storyBar.selectedCharacter, b.storyBar.selectedTypes, ::onSnippetClicked)
    }

    private fun onSnippetClicked(snippet: Snippet) {

    }


    private fun onStoryLineClicked(storyLine: StoryLine) {
        val list = selectedStoryLines.value!!.toMutableList()
        var allSelected = true
        var nonSelected = true
        for(sl in list) {
            if(storyLine == sl) sl.selected = !sl.selected
            if(!sl.selected) allSelected = false
            if(sl.selected) nonSelected = false
        }

        if(allSelected) selectAll()
        else {
            b.btnSelectALl.setImageResource(R.drawable.storyline_unselected)
            allSelected = false
        }
        if(nonSelected) deselectAll()
        selectedStoryLines.value = list
    }

    private fun onHeaderClicked() {
        popCreateStoryLine(b.storyLineRecycler)
    }

    private fun selectAll() {
        val newList = selectedStoryLines.value!!.toMutableList()
        for(sl in newList) {
            sl.selected = true
        }

        selectedStoryLines.value = newList
        b.btnSelectALl.setImageResource(R.drawable.storyline_selected)
        allSelected = true
    }

    private fun deselectAll() {
        val newList = selectedStoryLines.value!!.toMutableList()
        for(sl in newList) {
            sl.selected = false
        }
        selectedStoryLines.value = newList
        b.btnSelectALl.setImageResource(R.drawable.storyline_unselected)
        allSelected = false
    }

    private fun setClickListener() {
        b.btnSelectALl.setOnClickListener {
            if(allSelected) {
                deselectAll()
            }
            else {
                selectAll()
            }
        }
    }



    private fun setObserver() {
        Main.storyLineList.observe(context as LifecycleOwner) {
            val newList = selectedStoryLines.value!!.toMutableList()
            if (it != null) {
                for(sl in it) {
                    if(!newList.contains(sl)) newList.add(sl)
                }
                selectedStoryLines.value = newList
            }
        }
    }
}