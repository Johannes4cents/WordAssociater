package com.example.wordassociater.wordcat


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderWordCatBinding
import com.example.wordassociater.databinding.HolderWordCatListBinding
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.utils.LiveClass

class WordCatAdapter(
    val type: Type,
    private val onCatSelected: (wordCat: WordCat) -> Unit,
    val onHeaderClicked: (() -> Unit)? = null,
    val onDeleteClicked: (((wordcat: WordCat) -> Unit)?) = null): ListAdapter<WordCat, RecyclerView.ViewHolder>(WordCatDiff()) {
    enum class Type { BTN, BTNALL, List, SINGLEPICK, ALLOPTIONS }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val btnHolder = WordCatButtonHolder(HolderWordCatBinding.inflate(LayoutInflater.from(parent.context)), onCatSelected, onHeaderClicked)
        val listHolder = WordCatHolderList(HolderWordCatListBinding.inflate(LayoutInflater.from(parent.context)))
        return if(type == Type.BTN || type == Type.BTNALL) btnHolder else listHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(type == Type.BTN ||type == Type.BTNALL) (holder as WordCatButtonHolder).onBind(getItem(position))
        else (holder as WordCatHolderList).onBind(getItem(position), onCatSelected as (item:LiveClass) -> Unit)

    }
}

class WordCatDiff: DiffUtil.ItemCallback<WordCat>() {
    override fun areItemsTheSame(oldItem: WordCat, newItem: WordCat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WordCat, newItem: WordCat): Boolean {
        return oldItem.active == newItem.active
    }

}