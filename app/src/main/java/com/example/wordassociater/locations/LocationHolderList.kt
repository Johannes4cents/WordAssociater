package com.example.wordassociater.locations

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderSnippetPartListBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Event
import com.example.wordassociater.fire_classes.Location
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class LocationHolderList(val b: HolderSnippetPartListBinding): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item: LiveClass

    override fun onBind(item: LiveClass, takeItemFunc:((item: LiveClass) -> Unit)?) {
        this.item = item
        setContent()
        setObserver()
        setBackGroundColorFirstTime()

        b.root.setOnClickListener {
            takeItemFunc!!(item)
        }
    }

    private fun setContent() {
        b.partName.text = (item as Location).name
        if((item as Location).imgUrl != "") Glide.with(b.partImage).load((item as Location).imgUrl).into(b.partImage)
        else b.partImage.setImageResource((item as Event).getImage().getDrawable())

    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.partName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else  b.root.context.resources.getColor(
                    R.color.black))
        }
        DisplayFilter.observeItemColorDark(b.root.context, b.root, listOf(b.partName))
    }

    private fun setBackGroundColorFirstTime() {
        var dark = DisplayFilter.itemColorDark.value!!
        b.root.setBackgroundColor(if(dark) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(
                R.color.white))
        b.partName.setTextColor(if(dark) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(
                R.color.black))
    }
}