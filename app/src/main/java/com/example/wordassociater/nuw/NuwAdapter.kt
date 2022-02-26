package com.example.wordassociater.nuw

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderNuwBinding
import com.example.wordassociater.fire_classes.Nuw

class NuwAdapter(
        private val onUpgradeClicked: (nuw: Nuw) -> Unit,
        private val onRedXClicked: (nuw: Nuw) -> Unit,
        private val onRootClicked: (nuw: Nuw) -> Unit
)
    : ListAdapter<Nuw, RecyclerView.ViewHolder>(NuwDiff()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = NuwHolder(HolderNuwBinding.inflate(LayoutInflater.from(parent.context)), onUpgradeClicked, onRedXClicked, onRootClicked)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NuwHolder).onBind(getItem(position))
    }

}

class NuwDiff: DiffUtil.ItemCallback<Nuw>() {
    override fun areItemsTheSame(oldItem: Nuw, newItem: Nuw): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Nuw, newItem: Nuw): Boolean {
        return oldItem == newItem
    }

}