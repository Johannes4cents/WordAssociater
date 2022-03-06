package com.example.wordassociater.snippet_parts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentSnippetPartDetailedBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.SnippetPart
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.popups.popCreateStoryLine
import com.example.wordassociater.storylines.StoryLineRecycler
import com.example.wordassociater.utils.Page
import com.example.wordassociater.viewpager.ViewPagerMainFragment

class SnippetPartDetailedFragment: Fragment() {
    lateinit var b: FragmentSnippetPartDetailedBinding
    private val selectedStoryLines = MutableLiveData<List<StoryLine>>(mutableListOf())
    private val selectedWords = MutableLiveData<List<Word>>(mutableListOf())

    companion object {
        lateinit var snippetPart : SnippetPart
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentSnippetPartDetailedBinding.inflate(inflater)
        setNavigatorBar()
        setStoryLineRecycler()
        setTimeLine()
        return b.root
    }


    private fun setNavigatorBar() {
        b.navigationBar.initDetails(::onDetailsSelected, ::onTimeLineSelected)

        b.navigationBar.setBtnLeft(::onBackClicked, visibility = false)
        b.navigationBar.removeLeftBtn()

        b.navigationBar.setBtnRight(::onBackClicked, R.drawable.back_icon_mirrored)
    }

    private fun onBackClicked() {
        ViewPagerMainFragment.comingFrom = Page.SnippetParts
        findNavController().navigate(R.id.action_snippetPartDetailedFragment_to_ViewPagerFragment)
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
        b.storyLineRecycler.initRecycler(snippetPart.liveStoryLinesOnly, ::onStoryLineSelected, ::onHeaderSelected, true, mode = StoryLineRecycler.Mode.SnippetPart)
        snippetPart.getFullStoryLineList()
    }


    private fun onStoryLineSelected(storyLine: StoryLine) {
        val selectedLines = selectedStoryLines.value!!.toMutableList()
        if(selectedLines.contains(storyLine)) selectedLines.remove(storyLine)
        else selectedLines.add(storyLine)

        snippetPart.selectStoryLine(storyLine)
    }

    private fun onHeaderSelected() {
        popCreateStoryLine(b.navigationBar, StoryLine.Type.SnippetPart, snippetPart)
    }

    private fun setTimeLine() {
        b.timeLine.initTimeline(selectedStoryLines, null, null, null, ::onSnippetSelected)
    }

    private fun onSnippetSelected(snippet: Snippet) {

    }

}