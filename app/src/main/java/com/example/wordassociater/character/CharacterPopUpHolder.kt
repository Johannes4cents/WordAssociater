package com.example.wordassociater.character

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderCharacterPopupBinding
import com.example.wordassociater.fire_classes.Character

class CharacterPopUpHolder(val b: HolderCharacterPopupBinding): RecyclerView.ViewHolder(b.root) {
    lateinit var character: Character
    lateinit var takeCharacterFunc: (char: Character) -> Unit
    fun onBind(character: Character, takeCharacterFunc:( (char: Character) -> Unit)) {
        this.character = character
        this.takeCharacterFunc = takeCharacterFunc
        setContent()
        setClickListener()

    }

    private fun setContent() {
        Glide.with(b.root).load(character.imgUrl).into(b.characterPortrait)
        b.characterName.text = character.name

        b.buttonSelected.setImageResource(
                if(character.selected) R.drawable.checked_box else R.drawable.unchecked_box
        )

    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            takeCharacterFunc(character)
        }
    }



}
