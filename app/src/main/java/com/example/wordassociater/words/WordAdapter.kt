package com.example.wordassociater.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderWordPreviewBinding
import com.example.wordassociater.databinding.HolderWordSimpleBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.AdapterType

class WordAdapter(
        private val adapterType: AdapterType,
        private val takeWordFunc: (word: Word) -> Unit,
        private val rightButtonFunc: ((word:Word) -> Unit)?, )
    : ListAdapter<Word, RecyclerView.ViewHolder>(WordDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(adapterType == AdapterType.Preview) WordHolderPreview(HolderWordPreviewBinding.inflate(LayoutInflater.from(parent.context)))
        else WordHolderSimple(HolderWordSimpleBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val word = currentList[position]
        if(adapterType == AdapterType.Preview) (holder as WordHolderPreview).onBind(word)
        else (holder as WordHolderSimple).onBind(word, adapterType, takeWordFunc, rightButtonFunc)
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