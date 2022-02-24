package com.example.wordassociater.wordcat

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.WordCat

class WordCatRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    lateinit var wordCatAdapter: WordCatAdapter
    private var headerActive = true

    fun setupRecycler(onWordCatSelected: (wordCat: WordCat) -> Unit, liveList: MutableLiveData<List<WordCat>?>) {
        wordCatAdapter = WordCatAdapter(onWordCatSelected)
        setObserver(liveList)
    }

    fun setHeader(headerActive: Boolean) {
        this.headerActive = headerActive
    }

    private fun setObserver(liveList: MutableLiveData<List<WordCat>?>) {
        liveList.observe(context as LifecycleOwner) {
            val header = WordCat(0, "Manage")
            if(it != null) {
                if(headerActive) wordCatAdapter.submitList(it + listOf(header))
                else wordCatAdapter.submitList(it)
            }
            else {
                if(headerActive) wordCatAdapter.submitList(listOf(header))
            }
        }
    }
}