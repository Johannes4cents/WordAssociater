package com.example.wordassociater.dialogue

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Bubble

class BubbleAdapter(): ListAdapter<Bubble, RecyclerView.ViewHolder>(BubbleDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

class BubbleDiff: DiffUtil.ItemCallback<Bubble>() {
    override fun areItemsTheSame(oldItem: Bubble, newItem:  Bubble): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem:  Bubble, newItem:  Bubble): Boolean {
        return oldItem == newItem
    }

}