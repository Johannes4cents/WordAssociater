package com.example.wordassociater.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderSnippetPartPopupBinding
import com.example.wordassociater.fire_classes.Character

class CharacterSelectDialogueAdapter(
        val onCharSelected: (character: Character) -> Unit
): ListAdapter<Character, RecyclerView.ViewHolder>(CharacterDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = CharacterSelectDialogueHolder(HolderSnippetPartPopupBinding.inflate(LayoutInflater.from(parent.context)), onCharSelected)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CharacterSelectDialogueHolder).onBind(getItem(position))
    }
}


