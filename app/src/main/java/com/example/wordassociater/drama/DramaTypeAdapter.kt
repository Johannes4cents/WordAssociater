package com.example.wordassociater.drama


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderDramaTypeBinding
import com.example.wordassociater.utils.Drama

class DramaTypeAdapter(
        val takeDramaTypeFunc: (dramaType: Drama) -> Unit
): ListAdapter<Drama, RecyclerView.ViewHolder>(DramaTypeDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DramaTypeHolder(HolderDramaTypeBinding.inflate(LayoutInflater.from(parent.context)), takeDramaTypeFunc)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = getItem(position)
        (holder as DramaTypeHolder).onBind(type)
    }
}

class DramaTypeDiff: DiffUtil.ItemCallback<Drama>() {
    override fun areItemsTheSame(oldItem: Drama, newItem: Drama): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Drama, newItem: Drama): Boolean {
        return oldItem == newItem
    }

}