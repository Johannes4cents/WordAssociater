package com.example.wordassociater.character

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderCharacterPreviewBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Character

class CharacterHolderPreview(val b: HolderCharacterPreviewBinding, private val inStory: Boolean = false): RecyclerView.ViewHolder(b.root) {
    lateinit var character: Character
    fun onBind(character: Character) {
        b.characterName.text = character.name
        Glide.with(b.root.context).load(character.imgUrl).into(b.characterPortrait)
        setObserver()

    }

    private fun setObserver() {
        if(inStory) {
            DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
                b.characterName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else  b.root.context.resources.getColor(R.color.black))
            }
        }
    }
}