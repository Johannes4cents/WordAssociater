package com.example.wordassociater.character

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderSnippetPartListBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class CharacterHolderList(val b: HolderSnippetPartListBinding): RecyclerView.ViewHolder(b.root) , LiveHolder {
    lateinit var character: Character
    override lateinit var item: LiveClass

    var takeItemFunc:((item: LiveClass) -> Unit)? = null
    override fun onBind(character: LiveClass, takeItemFunc:((item: LiveClass) -> Unit)?) {
        this.character = character as Character
        this.takeItemFunc = takeItemFunc
        setContent()
        setClickListener()
        setObserver()
        setBackGroundColorFirstTime()
    }

    private fun setContent() {
        b.partName.text = character.name
        if(character.imgUrl != "") Glide.with(b.partImage).load(character.imgUrl).into(b.partImage)

        setClickListener()
    }
    private fun setClickListener() {
        b.root.setOnClickListener {
            takeItemFunc!!(character)
        }
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