package com.example.wordassociater.fams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderFamBinding
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.ItemTouchHelperAdapter

class FamAdapter(
        private val type: FamRecycler.Type,
        private val famLiveList: MutableLiveData<List<Fam>>,
        private val word: Word,
        private val onHeaderClicked: () -> Unit,
        private val takeFamFunc: (fam: Fam) -> Unit
): ListAdapter<Fam, RecyclerView.ViewHolder>(FamDiff()), ItemTouchHelperAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FamHolder(HolderFamBinding.inflate(LayoutInflater.from(parent.context)), onHeaderClicked, takeFamFunc)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FamHolder).onBind(type, getItem(position))
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return true
    }

    override fun onItemDismiss(position: Int) {
        val item = getItem(position)
        word.famList.remove(item)
        famLiveList.value = word.getFams()
    }
}

class FamDiff: DiffUtil.ItemCallback<Fam>() {
    override fun areItemsTheSame(oldItem: Fam, newItem: Fam): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Fam, newItem: Fam): Boolean {
        return oldItem == newItem
    }

}