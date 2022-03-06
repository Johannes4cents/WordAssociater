package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarPreviewsBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.StoryPart
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.utils.LiveClass

class PreviewBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    private val b = BarPreviewsBinding.inflate(LayoutInflater.from(context), this, true)
    private var useFilter = true
    private var storyPart: StoryPart? = null

    fun initBar(storyPart: StoryPart, useFilter: Boolean = true) {
        this.useFilter = useFilter
        this.storyPart = storyPart
        if(useFilter) setDisplayObserver()
        initRecycler()
    }

    private fun setDisplayObserver() {
        DisplayFilter.observeEventsShown(context, b.eventPreview)
        DisplayFilter.observeCharacterShown(context, b.characterPreview)
        DisplayFilter.observeItemsShown(context, b.itemPreview)
        DisplayFilter.observeLocationsShown(context, b.locationPreview)
        DisplayFilter.observeStoryLineShown(context, b.storyLinePreview)

        setVisibilityOnStartUp()

        DisplayFilter.observeBarColorDark(context, b.root)
    }

    private fun setVisibilityOnStartUp() {
        val charShown = DisplayFilter.characterShown.value
        var locationsShown = DisplayFilter.locationsShown.value
        var eventsShown = DisplayFilter.eventsShown.value
        val itemsShown = DisplayFilter.itemsShown.value
        val storyLinesShow = DisplayFilter.storyLineShown.value
        val barColorDark = DisplayFilter.barColorDark.value

        if(charShown != null) {
            b.characterPreview.visibility = if(charShown) View.VISIBLE else View.GONE
        }

        if(locationsShown != null) {
            b.locationPreview.visibility = if(locationsShown) View.VISIBLE else View.GONE
        }

        if(eventsShown != null) {
            b.eventPreview.visibility = if(eventsShown) View.VISIBLE else View.GONE
        }

        if(itemsShown != null) {
            b.itemPreview.visibility = if(itemsShown) View.VISIBLE else View.GONE
        }

        if(storyLinesShow != null) {
            b.storyLinePreview.visibility = if(storyLinesShow) View.VISIBLE else View.GONE
        }

        if(barColorDark != null) {
            b.root.setBackgroundColor(if(barColorDark) context.resources.getColor(R.color.snippets) else context.resources.getColor(R.color.white))
        }
    }

    private fun initRecycler() {
        if(storyPart != null) {
            b.itemPreview.initRecycler(LiveRecycler.Mode.Preview, LiveRecycler.Type.Item, null, storyPart!!.liveItems)
            b.locationPreview.initRecycler(LiveRecycler.Mode.Preview, LiveRecycler.Type.Location, null, storyPart!!.liveLocations)
            b.characterPreview.initRecycler(LiveRecycler.Mode.Preview, LiveRecycler.Type.Character, null, storyPart!!.liveCharacter)
            b.eventPreview.initRecycler(LiveRecycler.Mode.Preview, LiveRecycler.Type.Event, null, storyPart!!.liveEvents)
            b.storyLinePreview.initRecycler(LiveRecycler.Mode.Preview, LiveRecycler.Type.StoryLine, null, storyPart!!.liveSelectedStoryLines)

            storyPart!!.getFullItemList()
            storyPart!!.getFullLocationList()
            storyPart!!.getFullCharacterList()
            storyPart!!.getFullEventList()
            storyPart!!.getFullStoryLineList()
        }


        observeItemCount()
    }

    private fun observeItemCount() {
        storyPart!!.liveCharacter.observe(context as LifecycleOwner) {
            val selectedList = mutableListOf<LiveClass>()
            for(item in it) {
                if(item.selected) selectedList.add(item)
            }

            b.characterPreview.visibility = if(selectedList.count() > 1) View.VISIBLE else View.GONE
        }

        storyPart!!.liveLocations.observe(context as LifecycleOwner) {
            val selectedList = mutableListOf<LiveClass>()
            for(item in it) {
                if(item.selected) selectedList.add(item)
            }

            b.locationPreview.visibility = if(selectedList.count() > 1) View.VISIBLE else View.GONE
        }

        storyPart!!.liveItems.observe(context as LifecycleOwner) {
            val selectedList = mutableListOf<LiveClass>()
            for(item in it) {
                if(item.selected) selectedList.add(item)
            }

            b.itemPreview.visibility = if(selectedList.count() > 1) View.VISIBLE else View.GONE
        }

        storyPart!!.liveEvents.observe(context as LifecycleOwner) {
            val selectedList = mutableListOf<LiveClass>()
            for(item in it) {
                if(item.selected) selectedList.add(item)
            }

            b.eventPreview.visibility = if(selectedList.count() > 1) View.VISIBLE else View.GONE
        }

        storyPart!!.liveSelectedStoryLines.observe(context as LifecycleOwner) {
            val selectedList = mutableListOf<LiveClass>()
            for(item in it) {
                if(item.selected) selectedList.add(item)
            }

            b.storyLinePreview.visibility = if(selectedList.count() > 1) View.VISIBLE else View.GONE
        }
    }
}