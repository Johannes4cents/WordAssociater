package com.example.wordassociater.storylines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentStorylinesBinding
import com.example.wordassociater.fire_classes.StoryLine

class StoryLinesListFragment: Fragment() {
    lateinit var b : FragmentStorylinesBinding

    companion object {
        val liveStoryLineList = MutableLiveData<List<StoryLine>>()
        val selectedStoryLine = MutableLiveData<StoryLine>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentStorylinesBinding.inflate(inflater)
        setContent()
        setObserver()
        setClickListener()
        return b.root
    }

    private fun setContent() {
        b.storyLineRecycler.initRecycler(liveStoryLineList, ::onStoryLineClicked, ::onHeaderClicked)
    }

    private fun onStoryLineClicked(storyLine: StoryLine) {
        selectedStoryLine.value = storyLine
    }

    private fun onHeaderClicked() {

    }

    private fun setClickListener() {
        b.topBar.setDramaIconAndVisibility(R.drawable.icon_timeline, true)
        b.topBar.setNuwIconAndVisibility(R.drawable.icon_description, true)

    }

    private fun setObserver() {
        selectedStoryLine.observe(context as LifecycleOwner) {

        }
    }
}