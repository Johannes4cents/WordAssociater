package com.example.wordassociater.storylines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.FragmentStoryBinding
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.popups.popCreateStoryLine

class StoryFragment: Fragment() {
    lateinit var b : FragmentStoryBinding
    val selectedStoryLines = MutableLiveData<StoryLine>()

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
        b.storyLineRecycler.initRecycler(Main.storyLineList, ::onStoryLineClicked, ::onHeaderClicked)
    }


    private fun onStoryLineClicked(storyLine: StoryLine) {
    }

    private fun onHeaderClicked() {
        popCreateStoryLine(b.storyLineRecycler)
    }

    private fun setClickListener() {

    }

    private fun setObserver() {
    }
}