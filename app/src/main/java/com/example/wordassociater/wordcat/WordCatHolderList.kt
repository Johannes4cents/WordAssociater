package com.example.wordassociater.wordcat

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordCatListBinding
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class WordCatHolderList(
    val b : HolderWordCatListBinding)
    : RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item: LiveClass
    var takeItemFunc: (((item: LiveClass) -> Unit))? = null
    override fun onBind(item: LiveClass, takeItemFunc : ((item: LiveClass) -> Unit)?) {
        this.item = item
        this.takeItemFunc = takeItemFunc
        setContent()
        setClickListener()
    }

    private fun setContent() {
        b.plusSign.setImageResource((item as WordCat).getBg())
        b.catName.text = item.name
        b.checkBox.setImageResource(if ((item as WordCat).selected) R.drawable.checked_box else R.drawable.checkbox_unchecked)
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            takeItemFunc!!(item)
        }
    }
    }
