package com.example.wordassociater.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderSnippetPartListBinding
import com.example.wordassociater.databinding.HolderSnippetPartPopupBinding
import com.example.wordassociater.databinding.HolderSnippetPartPreviewBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.utils.LiveClass


class CharacterAdapter(
        var mode: CharacterRecycler.Mode,
        private val selectFunc: ((char: LiveClass) -> Unit)?,
        val fromStory: Boolean = false
)
    : ListAdapter<Character, RecyclerView.ViewHolder>(CharacterDiff()) {

    companion object {
        var selectedCharacterList = mutableListOf<Character>()
        var selectedNameChars = mutableListOf<Character>()
        var characterListTrigger = MutableLiveData<Unit>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val listHolder = CharacterHolderList(HolderSnippetPartListBinding.inflate(LayoutInflater.from(parent.context)))
        val prevewHolder = CharacterHolderPreview(HolderSnippetPartPreviewBinding.inflate(LayoutInflater.from(parent.context)))
        val popupHolder = CharacterHolderPopUp(HolderSnippetPartPopupBinding.inflate(LayoutInflater.from(parent.context)))
        return when(mode) {
            CharacterRecycler.Mode.Preview -> prevewHolder
            CharacterRecycler.Mode.Popup -> popupHolder
            CharacterRecycler.Mode.List -> listHolder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = currentList[position]
        when(mode) {
            CharacterRecycler.Mode.Preview -> (holder as CharacterHolderPreview).onBind(character)
            CharacterRecycler.Mode.Popup -> (holder as CharacterHolderPopUp).onBind(character, selectFunc!!)
            CharacterRecycler.Mode.List -> (holder as CharacterHolderList).onBind(character, selectFunc!!)
        }
    }
}

class CharacterDiff: DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return (oldItem.selected == newItem.selected)
    }
}