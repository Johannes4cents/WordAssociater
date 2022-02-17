package com.example.wordassociater.strain_list_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderStrainBinding
import com.example.wordassociater.fire_classes.Strain

class StrainAdapter: ListAdapter<Strain, RecyclerView.ViewHolder>(StrainDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StrainHolder(HolderStrainBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val strain = currentList[position]
        (holder as StrainHolder).onBind(strain)
    }
}

class StrainDiff: DiffUtil.ItemCallback<Strain>() {
    override fun areItemsTheSame(oldItem: Strain, newItem: Strain): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Strain, newItem: Strain): Boolean {
        return oldItem == newItem
    }
}