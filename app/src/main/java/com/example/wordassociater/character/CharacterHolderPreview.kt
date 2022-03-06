package com.example.wordassociater.character

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderSnippetPartPreviewBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class CharacterHolderPreview(val b: HolderSnippetPartPreviewBinding): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item: LiveClass
    override fun onBind(item: LiveClass, takeItemFunc:((item: LiveClass) -> Unit)?) {
        b.itemName.text = (item as Character).name
        Glide.with(b.root.context).load((item as Character).imgUrl).into(b.itemImage)
        setObserver()
        setBackGroundColorFirstTime()

    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.itemName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else  b.root.context.resources.getColor(R.color.black))
        }
        DisplayFilter.observeItemColorDark(b.root.context, b.root, listOf(b.itemName))
    }

    private fun setBackGroundColorFirstTime() {
        var dark = DisplayFilter.itemColorDark.value!!
        b.root.setBackgroundColor(if(dark) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(R.color.white))
        b.itemName.setTextColor(if(dark) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
    }
}