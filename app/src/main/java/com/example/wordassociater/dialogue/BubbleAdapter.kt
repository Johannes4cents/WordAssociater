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
import com.example.wordassociater.firestore.FireBubbles
import com.example.wordassociater.utils.ItemTouchHelperAdapter

class BubbleAdapter(
        private val mode: Mode,
        private val takeBubble: (bubble: Bubble) -> Unit,
        private val characterList: List<Character>?):
        ListAdapter<Bubble, RecyclerView.ViewHolder>(BubbleDiff()), ItemTouchHelperAdapter {

    enum class Mode { READ, WRITE }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bubbleBinding = HolderBubbleBinding.inflate(LayoutInflater.from(parent.context))
        val bubbleHolder = BubbleHolder(mode, bubbleBinding, takeBubble)
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

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onItemDismiss(position: Int) {
        val bubble = currentList[position]
        val newBubblelist = EditDialogueFragment.bubbleList.value!!.toMutableList()
        newBubblelist.remove(bubble)
        FireBubbles.delete(bubble.id, null)
        EditDialogueFragment.dialogue.bubbleList.remove(bubble.id)
        EditDialogueFragment.bubbleList.value = newBubblelist
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