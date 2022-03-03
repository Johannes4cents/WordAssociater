package com.example.wordassociater.words

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordPreviewBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Word

class WordHolderPreview(val b : HolderWordPreviewBinding, private val fromStory: Boolean = false): RecyclerView.ViewHolder(b.root) {
    private lateinit var word : Word


    fun onBind(word: Word) {
        this.word = word
        setContent()
        setObserver()
        setBackGroundColorFirstTime()
    }

    private fun setContent() {
        b.content.text = word.text
        if(word.id == 0L) b.root.visibility = View.GONE
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