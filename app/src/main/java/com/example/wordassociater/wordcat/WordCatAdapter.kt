package com.example.wordassociater.wordcat


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderWordCatBinding
import com.example.wordassociater.fire_classes.WordCat

class WordCatAdapter(val onCatSelected: (wordCat: WordCat) -> Unit): ListAdapter<WordCat, RecyclerView.ViewHolder>(WordCatDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = WordCatHolder(HolderWordCatBinding.inflate(LayoutInflater.from(parent.context)), onCatSelected)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WordCatHolder).onBind(getItem(position))
    }
}

class WordCatDiff: DiffUtil.ItemCallback<WordCat>() {
    override fun areItemsTheSame(oldItem: WordCat, newItem: WordCat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WordCat, newItem: WordCat): Boolean {
        return oldItem == newItem
    }

}