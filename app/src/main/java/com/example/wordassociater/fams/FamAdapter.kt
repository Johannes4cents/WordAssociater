package com.example.wordassociater.fams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderFamBinding
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.utils.CommonWord

class FamAdapter(
        private val type: FamRecycler.Type,
        private val onFamClicked: (fam: Fam) -> Unit,
        private val onUpgradeFam: (fam: Fam) -> Unit,
        private val onMakeCommonWord: (fam: Fam, type: CommonWord.Type) -> Unit,
): ListAdapter<Fam, RecyclerView.ViewHolder>(FamDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FamHolder(HolderFamBinding.inflate(LayoutInflater.from(parent.context)), onFamClicked, onUpgradeFam, onMakeCommonWord)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FamHolder).onBind(type, getItem(position))
    }


class FamDiff: DiffUtil.ItemCallback<Fam>() {
    override fun areItemsTheSame(oldItem: Fam, newItem: Fam): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Fam, newItem: Fam): Boolean {
        return oldItem == newItem
    }
}

}