package com.example.wordassociater.character

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderSnippetPartPopupBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Character

class CharacterHolderPopUp(val b: HolderSnippetPartPopupBinding): RecyclerView.ViewHolder(b.root) {
    lateinit var character: Character
    lateinit var takeCharacterFunc: (char: Character) -> Unit
    fun onBind(character: Character, takeCharacterFunc:( (char: Character) -> Unit)) {
        this.character = character
        this.takeCharacterFunc = takeCharacterFunc
        setContent()
        setClickListener()
        setObserver()
        setBackGroundColorFirstTime()

    }

    private fun setContent() {
        Glide.with(b.root).load(character.imgUrl).into(b.partImage)
        b.characterName.text = character.name

        b.checkbox.setImageResource(
                if(character.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked
        )

    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            takeCharacterFunc(character)
        }
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
