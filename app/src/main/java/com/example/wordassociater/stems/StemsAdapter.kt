package com.example.wordassociater.stems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderStemBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.ItemTouchHelperAdapter

class StemAdapter(
        private val stemLiveList: MutableLiveData<List<String>>,
        private val word: Word,
        private val onHeaderClicked: () -> Unit,
        private val takeStemFunc: (stem: String) -> Unit
): ListAdapter<String, RecyclerView.ViewHolder>(SynDiff()), ItemTouchHelperAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StemsHolder(HolderStemBinding.inflate(LayoutInflater.from(parent.context)), onHeaderClicked, takeStemFunc)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StemsHolder).onBind(getItem(position))
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return true
    }

    override fun onItemDismiss(position: Int) {
        val item = getItem(position)
        word.stems.remove(item)
        stemLiveList.value = word.stems
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