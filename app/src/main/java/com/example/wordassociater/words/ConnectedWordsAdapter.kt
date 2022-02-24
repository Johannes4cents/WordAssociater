package com.example.wordassociater.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderConnectedWordBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.ConnectedWord

class ConnectedWordsAdapter(val onWordSelected: (word: Word) -> Unit): ListAdapter<ConnectedWord, RecyclerView.ViewHolder>(ConnectedWordDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ConnectedWordHolder(HolderConnectedWordBinding.inflate(LayoutInflater.from(parent.context)), onWordSelected)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ConnectedWordHolder).onBind(getItem(position))
    }
}



class ConnectedWordDiff: DiffUtil.ItemCallback<ConnectedWord>() {
    override fun areItemsTheSame(oldItem: ConnectedWord, newItem: ConnectedWord): Boolean {
        return oldItem.word == newItem.word
    }

    override fun areContentsTheSame(oldItem: ConnectedWord, newItem: ConnectedWord): Boolean {
        return oldItem == newItem
    }

}