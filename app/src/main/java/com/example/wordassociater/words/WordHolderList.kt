package com.example.wordassociater.words

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordListBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class WordHolderList(
        val b : HolderWordListBinding
): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item : LiveClass
    var takeItemFunc:((item: LiveClass) -> Unit)? = null

    override fun onBind(item: LiveClass, takeItemFunc:((item: LiveClass) -> Unit)?) {
        this.item = item
        this.takeItemFunc = takeItemFunc
        setContent()
        setClickListener()
        setObserver()
    }

    private fun setContent() {
        b.content.text = (item as Word).text
        if((item as Word).cats.isNotEmpty()) {
            b.wordCatIcon.setImageResource(Main.getWordCat((item as Word).cats[0])!!.getBg())
        }
        else {
            b.wordCatIcon.setImageResource(R.drawable.wordcat_bg_none)
        }

        b.stemCount.text = (item as Word).stems.count().toString()
        b.idField.text = item.id.toString()
        b.usedOrConnectionsField.text = (item as Word).used.toString()
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            takeItemFunc!!(item as Word)
        }
    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.content.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
        }
    }
}