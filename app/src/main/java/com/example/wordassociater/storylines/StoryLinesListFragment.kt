package com.example.wordassociater.storylines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentStorylinesBinding
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.popups.popCreateStoryLine

class StoryLinesListFragment: Fragment() {
    lateinit var b : FragmentStorylinesBinding

    companion object {
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
        b.storyLineRecycler.initRecycler(Main.storyLineList, ::onStoryLineClicked, ::onHeaderClicked)
        b.topBar.setDramaIconAndVisibility(R.drawable.icon_timeline, true)
        b.topBar.setNuwIconAndVisibility(R.drawable.icon_description, true)
        b.topBar.setSaveIconAndVisibility(R.drawable.back_icon_mirrored, true)
        b.topBar.showLeftText("Story \n Lines")
    }

    private fun onStoryLineClicked(storyLine: StoryLine) {
        selectedStoryLine.value = storyLine
    }

    private fun onHeaderClicked() {
        popCreateStoryLine(b.storyLineRecycler)
    }

    private fun setClickListener() {
        b.topBar.setSaveButton {
            findNavController().navigate(R.id.action_storyLinesListFragment_to_ViewPagerFragment)
        }
    }

    private fun setObserver() {
        selectedStoryLine.observe(context as LifecycleOwner) {

        }

    }
}