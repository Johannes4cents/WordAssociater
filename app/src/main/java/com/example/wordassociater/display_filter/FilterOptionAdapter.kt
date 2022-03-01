package com.example.wordassociater.display_filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderFilterOptionBinding

class FilterOptionAdapter(val onFilterSelected: (option: FilterOption) -> Unit): ListAdapter<FilterOption, RecyclerView.ViewHolder>(FilterOptionDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilterOptionHolder(HolderFilterOptionBinding.inflate(LayoutInflater.from(parent.context)), onFilterSelected)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FilterOptionHolder).onBind(getItem(position))
    }
}

class FilterOptionDiff: DiffUtil.ItemCallback<FilterOption>() {
    override fun areItemsTheSame(oldItem: FilterOption, newItem: FilterOption): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: FilterOption, newItem: FilterOption): Boolean {
        return oldItem == newItem
    }

}