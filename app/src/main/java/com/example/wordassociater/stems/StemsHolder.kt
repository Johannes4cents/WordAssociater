package com.example.wordassociater.stems

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderStemBinding
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class StemsHolder(
        val b: HolderStemBinding,
        ): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item: LiveClass
    override fun onBind(item: LiveClass, takeItemFunc: ((item: LiveClass) -> Unit)?) {
        this.item = item
        b.stemText.text = item.name
        b.root.setOnClickListener { takeItemFunc!!(item) }
    }

}