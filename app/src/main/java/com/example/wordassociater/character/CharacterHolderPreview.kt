package com.example.wordassociater.character

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.databinding.HolderCharacterPreviewBinding
import com.example.wordassociater.fire_classes.Character

class CharacterHolderPreview(val b: HolderCharacterPreviewBinding): RecyclerView.ViewHolder(b.root) {
    lateinit var character: Character
    fun onBind(character: Character) {
        b.characterName.text = character.name
        Glide.with(b.root.context).load(character.imgUrl).into(b.characterPortrait)

    }
}