package com.example.wordassociater.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderImageBinding
import com.example.wordassociater.utils.Image
import com.example.wordassociater.utils.LiveClass

class ImageAdapter(private val onImageSelected: (image: LiveClass) -> Unit): ListAdapter<Image, RecyclerView.ViewHolder>(ImageDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageHolder(HolderImageBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageHolder).onBind(getItem(position), onImageSelected)
    }

}

class ImageDiff: DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}