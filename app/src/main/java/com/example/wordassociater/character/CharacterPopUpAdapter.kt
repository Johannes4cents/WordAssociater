package com.example.wordassociater.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderCharacterPopupBinding
import com.example.wordassociater.fire_classes.Character

class CharacterPopUpAdapter(
        private val selectFunc: (char: Character) -> Unit
)
    : ListAdapter<Character, RecyclerView.ViewHolder>(CharacterDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterPopUpHolder(HolderCharacterPopupBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = currentList[position]
        (holder as CharacterPopUpHolder).onBind(character, selectFunc)
    }
}

