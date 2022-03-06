package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.PopLiveClassBinding
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.LiveClass

fun popLiveClass(type: LiveRecycler.Type, from: View, liveList: MutableLiveData<List<LiveClass>>, onItemClicked: (item: LiveClass) -> Unit, fromMiddle: Boolean = false) {
    val b = PopLiveClassBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop= Helper.getPopUp(b.root, from, fromMiddle = fromMiddle)
    b.liveRecycler.initRecycler(LiveRecycler.Mode.Popup, type, onItemClicked, liveList)

    b.iconType.setImageResource(
            when(type) {
                LiveRecycler.Type.Character -> R.drawable.icon_character
                LiveRecycler.Type.Item -> R.drawable.icon_item
                LiveRecycler.Type.Event -> R.drawable.event_icon
                LiveRecycler.Type.Location -> R.drawable.icon_location
                LiveRecycler.Type.StoryLine -> R.drawable.icon_story
                LiveRecycler.Type.Word -> R.drawable.icon_word
                LiveRecycler.Type.WordCat -> R.drawable.wordcat_bg_black
            }
    )

    b.btnBack.setOnClickListener { pop.dismiss() }
}