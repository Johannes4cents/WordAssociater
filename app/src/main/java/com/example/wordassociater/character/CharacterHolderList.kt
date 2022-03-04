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

    lateinit var takeCharacterFunc: (char: Character) -> Unit
    override fun onBind(character: LiveClass, takeCharacterFunc:( (char: LiveClass) -> Unit)) {
        this.character = character as Character
        this.takeCharacterFunc = takeCharacterFunc
        setContent()
        setClickListener()
        setObserver()
        setBackGroundColorFirstTime()
    }

    private fun setContent() {
        b.characterName.text = character.name
        if(character.imgUrl != "") Glide.with(b.characterPortrait).load(character.imgUrl).into(b.characterPortrait)

        setClickListener()
    }
    private fun setClickListener() {
        b.root.setOnClickListener {
            takeCharacterFunc(character)
        }
    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.characterName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else  b.root.context.resources.getColor(
                R.color.black))
        }
        DisplayFilter.observeItemColorDark(b.root.context, b.root, listOf(b.characterName))
    }

    private fun setBackGroundColorFirstTime() {
        var dark = DisplayFilter.itemColorDark.value!!
        b.root.setBackgroundColor(if(dark) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(
            R.color.white))
        b.characterName.setTextColor(if(dark) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(
            R.color.black))
    }



}