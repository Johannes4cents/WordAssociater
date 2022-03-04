package com.example.wordassociater.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HeaderSearchWordBinding
import com.example.wordassociater.databinding.HolderWordPreviewBinding
import com.example.wordassociater.databinding.HolderWordSimpleBinding
import com.example.wordassociater.fire_classes.Word

class WordAdapter(
        private val mode: WordRecycler.Mode,
        private val takeWordFunc: ((word: Word) -> Unit)?,
        private val onHeaderClicked: ((text: String) -> Unit)? = null,
        )
    : ListAdapter<Word, RecyclerView.ViewHolder>(WordDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val previewHolder = WordHolderPreview(HolderWordPreviewBinding.inflate(LayoutInflater.from(parent.context)))
        val listHolder = WordHolderList(HolderWordSimpleBinding.inflate(LayoutInflater.from(parent.context)), takeWordFunc!!)
        val popupHolder = WordHolderPopup(HolderWordSimpleBinding.inflate(LayoutInflater.from(parent.context)), takeWordFunc)
        val header = SearchWordsHeader(HeaderSearchWordBinding.inflate(LayoutInflater.from(parent.context)), onHeaderClicked)
        return when(mode) {
            WordRecycler.Mode.Preview -> previewHolder
            WordRecycler.Mode.Popup -> {
                if(viewType == 1) popupHolder else header
            }
            WordRecycler.Mode.List -> listHolder
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val word = currentList[position]
        word.adapterPosition = position
        when(mode) {
            WordRecycler.Mode.Preview -> (holder as WordHolderPreview).onBind(word)
            WordRecycler.Mode.Popup -> {
                if(word.isAHeader) (holder as SearchWordsHeader).onBind(word) else (holder as WordHolderPopup).onBind(word)
            }
            WordRecycler.Mode.List -> (holder as WordHolderList).onBind(word)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val word = getItem(position)
        return if(word.isAHeader) 2 else 1
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