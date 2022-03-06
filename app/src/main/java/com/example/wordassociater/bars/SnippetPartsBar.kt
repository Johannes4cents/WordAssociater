package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarSnippetPartsBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.popups.popLiveClass
import com.example.wordassociater.utils.LiveClass

class SnippetPartsBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarSnippetPartsBinding.inflate(LayoutInflater.from(context), this, true)

    fun initSnippetsBar() {
        setObserver()
    }

    fun setLocationsPopup(liveList: MutableLiveData<List<LiveClass>>, onItemSelected: (item: LiveClass) -> Unit) {
        b.btnLocation.setOnClickListener {
            popLiveClass(LiveRecycler.Type.Location, it, liveList, onItemSelected)
        }
    }

    fun setEventsPopup(liveList: MutableLiveData<List<LiveClass>>, onItemSelected: (item: LiveClass) -> Unit) {
        b.btnEvents.setOnClickListener {
            popLiveClass(LiveRecycler.Type.Event, it, liveList, onItemSelected)
        }
    }

    fun setItemsPopup(liveList: MutableLiveData<List<LiveClass>>, onItemSelected: (item: LiveClass) -> Unit) {
        b.btnItems.setOnClickListener {
            popLiveClass(LiveRecycler.Type.Item, it, liveList, onItemSelected)
        }
    }

    fun setCharacterPopup(liveList: MutableLiveData<List<LiveClass>>, onItemSelected: (item: LiveClass) -> Unit) {
        b.btnCharacter.setOnClickListener {
            popLiveClass(LiveRecycler.Type.Character, it, liveList, onItemSelected)
        }
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