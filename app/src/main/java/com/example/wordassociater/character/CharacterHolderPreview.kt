package com.example.wordassociater.character

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderSnippetPartPreviewBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Character

class CharacterHolderPreview(val b: HolderSnippetPartPreviewBinding): RecyclerView.ViewHolder(b.root) {
    lateinit var character: Character
    fun onBind(character: Character) {
        b.characterName.text = character.name
        Glide.with(b.root.context).load(character.imgUrl).into(b.characterPortrait)
        setObserver()
        setBackGroundColorFirstTime()

    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.characterName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else  b.root.context.resources.getColor(R.color.black))
        }
        DisplayFilter.observeItemColorDark(b.root.context, b.root, listOf(b.characterName))
    }

    private fun setBackGroundColorFirstTime() {
        var dark = DisplayFilter.itemColorDark.value!!
        b.root.setBackgroundColor(if(dark) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(R.color.white))
        b.characterName.setTextColor(if(dark) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
    }
}