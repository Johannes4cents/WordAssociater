package com.example.wordassociater.character

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderSnippetPartPopupBinding
import com.example.wordassociater.fire_classes.Character

class CharacterSelectDialogueHolder(
        val b : HolderSnippetPartPopupBinding,
        val onCharSelected: (character: Character) -> Unit): RecyclerView.ViewHolder(b.root) {

    lateinit var character: Character
    fun onBind(character: Character) {
        this.character = character
        setClickListener()
        setContent()
    }

    private fun setContent() {
        b.checkbox.setImageResource(if(character.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked)
        b.characterName.text = character.name
        Glide.with(b.root.context).load(character.imgUrl).into(b.partImage)
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onCharSelected(character)
        }
    }
}