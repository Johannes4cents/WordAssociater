package com.example.wordassociater.character

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderCharacterPopupBinding
import com.example.wordassociater.fire_classes.Character

class CharacterSelectDialogueHolder(
        val b : HolderCharacterPopupBinding,
        val onCharSelected: (character: Character) -> Unit): RecyclerView.ViewHolder(b.root) {

    lateinit var character: Character
    fun onBind(character: Character) {
        this.character = character
        setClickListener()
        setContent()
    }

    private fun setContent() {
        b.buttonSelected.setImageResource(if(character.selected) R.drawable.checked_box else R.drawable.unchecked_box)
        b.characterName.text = character.name
        Glide.with(b.root.context).load(character.imgUrl).into(b.characterPortrait)
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onCharSelected(character)
        }
    }
}