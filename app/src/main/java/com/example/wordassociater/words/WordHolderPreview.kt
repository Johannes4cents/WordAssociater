package com.example.wordassociater.words

import android.view.View
import androidx.lifecycle.LifecycleOwner
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
    }

    private fun setContent() {
        b.content.text = word.text
        if(word.id == 0L) b.root.visibility = View.GONE
    }

    private fun setObserver() {
        if(fromStory) {
            DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
                b.content.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(
                    R.color.black))
            }
        }
    }



}