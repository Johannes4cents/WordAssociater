package com.example.wordassociater.dramaturgy


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderDramaTypeBinding
import com.example.wordassociater.fire_classes.Drama

class DramaTypeAdapter(
        val takeDramaTypeFunc: (dramaType: Drama.Type) -> Unit
): ListAdapter<Drama.Type, RecyclerView.ViewHolder>(DramaTypeDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DramaTypeHolder(HolderDramaTypeBinding.inflate(LayoutInflater.from(parent.context)), takeDramaTypeFunc)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = getItem(position)
        (holder as DramaTypeHolder).onBind(type)
    }
}

class DramaTypeDiff: DiffUtil.ItemCallback<Drama.Type>() {
    override fun areItemsTheSame(oldItem: Drama.Type, newItem: Drama.Type): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Drama.Type, newItem: Drama.Type): Boolean {
        return oldItem == newItem
    }

}