package com.example.wordassociater.dialogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderDialogueBinding
import com.example.wordassociater.fire_classes.Dialogue
import com.example.wordassociater.utils.ItemTouchHelperAdapter

class DialogueAdapter(val selectDialogueFunc: (dialogue: Dialogue) -> Unit): ListAdapter<Dialogue, RecyclerView.ViewHolder>(DialogueDiff()), ItemTouchHelperAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DialogueHolder(HolderDialogueBinding.inflate(LayoutInflater.from(parent.context)), selectDialogueFunc)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DialogueHolder).onBind(getItem(position))
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onItemDismiss(position: Int) {
        TODO("Not yet implemented")
    }

}

class DialogueDiff: DiffUtil.ItemCallback<Dialogue>() {
    override fun areItemsTheSame(oldItem: Dialogue, newItem: Dialogue): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Dialogue, newItem: Dialogue): Boolean {
        return  oldItem == newItem
    }

}