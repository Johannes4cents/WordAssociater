package com.example.wordassociater.words

import android.view.View
import androidx.core.view.updatePadding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordSimpleBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.AdapterType

class WordHolderSimple(val b : HolderWordSimpleBinding, private val fromStory: Boolean = false): RecyclerView.ViewHolder(b.root) {
    private lateinit var word : Word
    private lateinit var takeWordFunc: (word: Word) -> Unit
    private lateinit var adapterType: AdapterType
    private var rightButtonFunc: ((word: Word) -> Unit)? = null
    private var firstSet = true

    fun onBind(word: Word, adapterType: AdapterType, takeWordFunc: (word: Word) -> Unit, rightButtonFunc: ((word: Word) -> Unit) ? = null) {
        this.takeWordFunc = takeWordFunc
        this.word = word
        this.rightButtonFunc = rightButtonFunc
        this.adapterType = adapterType
        setContent()
        setClickListener()
        setObserver()
    }

    private fun setContent() {
        if(word.id == 0L) b.root.visibility = View.GONE
        b.content.text = word.text
        if(adapterType == AdapterType.Popup) b.checkbox.setImageResource(if(word.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked)
        else {
            if(word.cats.isNotEmpty()) {
                b.checkbox.setImageResource(Main.getWordCat(word.cats[0])!!.getBg())
            }
            else {
                b.checkbox.setImageResource(R.drawable.wordcat_bg_none)
            }
            b.checkbox.updatePadding(10 , 10 ,10 ,10)
            b.checkbox.requestLayout()
        }

        b.stemCount.text = word.stems.count().toString()
        b.idField.text = word.id.toString()
        b.usedOrConnectionsField.text = word.used.toString()
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            b.checkbox.setImageResource(if(!word.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked)
            takeWordFunc(word)
        }

        b.checkbox.setOnClickListener {
            if(rightButtonFunc != null) rightButtonFunc!!(word)
        }
    }

    private fun setObserver() {
        if(fromStory) {
            DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
                b.content.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
            }
        }
    }




}