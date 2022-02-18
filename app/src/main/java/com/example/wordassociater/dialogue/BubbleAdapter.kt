package com.example.wordassociater.dialogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HeaderBubbleBinding
import com.example.wordassociater.databinding.HolderBubbleBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.fire_classes.Character

class BubbleAdapter(private val takeBubble: (bubble: Bubble) -> Unit, private val characterList: List<Character>): ListAdapter<Bubble, RecyclerView.ViewHolder>(BubbleDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bubbleBinding = HolderBubbleBinding.inflate(LayoutInflater.from(parent.context))
        val bubbleHolder = BubbleHolder(bubbleBinding, takeBubble)
        val bubbleHeaderBinding = HeaderBubbleBinding.inflate(LayoutInflater.from(parent.context))
        val bubbleHeader = BubbleHeader(bubbleHeaderBinding, characterList, takeBubble)
        return when(viewType) {
            0 -> bubbleHolder
            else -> bubbleHeader
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if(item.isHeader) {
            (holder as BubbleHeader).onBind()
        }
        else {
            (holder as BubbleHolder).onBind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val bubble = getItem(position)
        return when(bubble.isHeader) {
            false -> 0
            true -> 1
        }
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