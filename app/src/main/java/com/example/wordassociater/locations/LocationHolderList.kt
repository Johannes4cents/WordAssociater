package com.example.wordassociater.locations

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderSnippetPartListBinding
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class LocationHolderList(val b: HolderSnippetPartListBinding): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item: LiveClass

    override fun onBind(item: LiveClass, takeItemFunc: (item: LiveClass) -> Unit) {
        this.item = item

        b.root.setOnClickListener {
            takeItemFunc(item)
        }
    }

    private fun setContent() {

    }
}