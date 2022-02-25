package com.example.wordassociater.wordcat


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderWordCatBinding
import com.example.wordassociater.databinding.HolderWordCatListBinding
import com.example.wordassociater.fire_classes.WordCat

class WordCatAdapter(val type: Type, private val onCatSelected: (wordCat: WordCat) -> Unit): ListAdapter<WordCat, RecyclerView.ViewHolder>(WordCatDiff()) {
    enum class Type { BTN, List, SINGLEPICK }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val btnHolder = WordCatButtonHolder(HolderWordCatBinding.inflate(LayoutInflater.from(parent.context)), onCatSelected)
        val listHolder = WordCatListHolder(type, HolderWordCatListBinding.inflate(LayoutInflater.from(parent.context)), onCatSelected)
        return if(type == Type.BTN) btnHolder else listHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(type == Type.BTN) (holder as WordCatButtonHolder).onBind(getItem(position))
        else (holder as WordCatListHolder).onBind(getItem(position))

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