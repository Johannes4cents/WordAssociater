package com.example.wordassociater.synonyms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderSynonymBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.ItemTouchHelperAdapter

class SynonymAdapter(
        private val type: SynonymRecycler.Type,
        private val synonymLiveList: MutableLiveData<List<String>>,
        private val word: Word,
        private val onHeaderClicked: () -> Unit,
        private val takeSynonymFunc: (synonym: String) -> Unit
): ListAdapter<String, RecyclerView.ViewHolder>(SynDiff()), ItemTouchHelperAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SynonymHolder(HolderSynonymBinding.inflate(LayoutInflater.from(parent.context)), onHeaderClicked, takeSynonymFunc)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SynonymHolder).onBind(type, getItem(position))
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return true
    }

    override fun onItemDismiss(position: Int) {
        val item = getItem(position)
        word.synonyms.remove(item)
        synonymLiveList.value = word.synonyms
    }
}

class SynDiff: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}