package com.example.wordassociater.words_list_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderWordBinding
import com.example.wordassociater.databinding.HolderWordPopupBinding
import com.example.wordassociater.popups.WHolder
import com.example.wordassociater.start_fragment.Word

class WordAdapter(
        private val onClickFunc: (word: Word) -> Unit,
        private val btnNewWordFunc: (word:Word) -> Unit): ListAdapter<Word, RecyclerView.ViewHolder>(WordDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  WHolder(HolderWordPopupBinding.inflate(LayoutInflater.from(parent.context)), true)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val word = currentList[position]
        (holder as WHolder).onBind(word, onClickFunc, btnNewWordFunc)
    }
}

class WordDiff: DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}