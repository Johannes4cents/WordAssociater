package com.example.wordassociater.words

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordPreviewBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class WordHolderPreview(val b : HolderWordPreviewBinding): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item : LiveClass


    override fun onBind(item: LiveClass, takeItemFunc:((item: LiveClass) -> Unit)?) {
        this.item = item
        setContent()
        setObserver()
        setBackGroundColorFirstTime()
    }

    private fun setContent() {
        b.content.text = (item as Word).text
        if(item.id == 0L) b.root.visibility = View.GONE
    }

    private fun setObserver() {
        DisplayFilter.observeItemColorDark(b.root.context, b.root, listOf(b.content))
    }

    private fun setBackGroundColorFirstTime() {
        var dark = DisplayFilter.itemColorDark.value!!
        b.root.setBackgroundColor(if(dark) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(R.color.white))
        b.content.setTextColor(if(dark) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
    }



}