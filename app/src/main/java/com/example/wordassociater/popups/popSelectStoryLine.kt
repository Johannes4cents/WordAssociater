package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.PopupSelectStorylineBinding
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.utils.Helper

fun popSelectStoryLine(from: View, onStoryLineSelected: (storyLine: StoryLine) -> Unit, liveList: MutableLiveData<List<StoryLine>>, fromMiddle: Boolean = true) {
    val b = PopupSelectStorylineBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, null, null, null, null, fromMiddle)

    b.storyLineRecycler.initRecycler(liveList, onStoryLineSelected, null, false)

    b.btnRight.visibility = View.GONE
    b.btnLeft.setOnClickListener {
        pop.dismiss()
    }
}