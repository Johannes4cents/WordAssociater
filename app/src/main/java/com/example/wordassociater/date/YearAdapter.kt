package com.example.wordassociater.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderYearBinding
import com.example.wordassociater.stems.SynDiff

class YearAdapter(private val onYearSelected: (year: String) -> Unit, val liveDate: MutableLiveData<String>): ListAdapter<String, RecyclerView.ViewHolder>(SynDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return YearHolder(HolderYearBinding.inflate(LayoutInflater.from(parent.context)), onYearSelected)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as YearHolder).onBind(getItem(position), liveDate)
    }
}

