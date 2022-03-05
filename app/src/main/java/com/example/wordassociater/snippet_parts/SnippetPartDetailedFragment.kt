package com.example.wordassociater.snippet_parts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.FragmentSnippetPartDetailedBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.SnippetPart
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.fire_classes.Word

class SnippetPartDetailedFragment: Fragment() {
    lateinit var b: FragmentSnippetPartDetailedBinding
    private val selectedStoryLines = MutableLiveData<List<StoryLine>>(mutableListOf())
    private val selectedWords = MutableLiveData<List<Word>>(mutableListOf())

    companion object {
        lateinit var snippetPart : SnippetPart
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentSnippetPartDetailedBinding.inflate(inflater)
        setTopBar()
        setNavigatorBar()
        setStoryLineRecycler()
        setTimeLine()
        return b.root
    }

    private fun setTopBar() {

    }

    private fun setNavigatorBar() {
        b.navigationBar.initDetails(::onDetailsSelected, ::onTimeLineSelected)
    }

    private fun onTimeLineSelected() {
        b.details.visibility = View.GONE
        b.timeLine.visibility = View.VISIBLE
        setTimeLine()
    }

    private fun onDetailsSelected() {
        b.details.visibility = View.VISIBLE
        b.timeLine.visibility = View.GONE
    }

    private fun setStoryLineRecycler() {
        b.storyLineRecycler.initRecycler(snippetPart.liveStoryLines, ::onStoryLineSelected, ::onHeaderSelected)
    }

    private fun onStoryLineSelected(storyLine: StoryLine) {
        val storyLineList = selectedStoryLines.value!!.toMutableList()
        if(storyLineList.contains(storyLine)) storyLineList.remove(storyLine)
        else storyLineList.add(storyLine)
        selectedStoryLines.value = storyLineList
    }

    private fun onHeaderSelected() {

    }

    private fun setTimeLine() {
        b.timeLine.initTimeline(selectedStoryLines, null, null, null, ::onSnippetSelected)
    }

    private fun onSnippetSelected(snippet: Snippet) {

    }

}