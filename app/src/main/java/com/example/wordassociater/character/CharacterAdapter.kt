package com.example.wordassociater.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderCharacterBinding
import com.example.wordassociater.fire_classes.Character


class CharacterAdapter(
        var mode: Mode, val characterList : MutableList<Character>? = null,
        val selectFunc: ((char: Character) -> Unit)? = null): ListAdapter<Character, RecyclerView.ViewHolder>(CharacterDiff()) {
    public enum class Mode { MAIN, LIST, SELECT, PREVIEW, UPDATE, CONNECTSNIPPETS}

    companion object {
        var selectedCharacterList = mutableListOf<Character>()
        var selectedNameChars = mutableListOf<Character>()
        var characterListTrigger = MutableLiveData<Unit>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterHolder(HolderCharacterBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = currentList[position]
        (holder as CharacterHolder).onBind(character, this, selectFunc)
    }
}

class CharacterDiff: DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}