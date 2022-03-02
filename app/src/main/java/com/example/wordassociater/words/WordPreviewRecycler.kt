package com.example.wordassociater.words

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.AdapterType

class WordPreviewRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    lateinit var previewAdapter: WordAdapter
    lateinit var liveList: MutableLiveData<List<Word>>
    lateinit var onWordClicked: (word:Word) -> Unit

    fun initRecycler(liveList: MutableLiveData<List<Word>>,fromStory: Boolean = false) {
        previewAdapter = WordAdapter(AdapterType.Preview, ::onWordClicked, null, fromStory =  fromStory)
        adapter = previewAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
        this.liveList = liveList
        previewAdapter.submitList(liveList.value)
        setObserver()
    }

    fun submitList(list: List<Word>) {
        previewAdapter.submitList(list)
    }

    private fun onWordClicked(word: Word) {

    }

    private fun setObserver() {
        liveList.observe(context as LifecycleOwner) {
            val selectedChars = mutableListOf<Word>()
            for(w in it) {
                if(w.selected && !selectedChars.contains(w)) selectedChars.add(w)
            }
            previewAdapter.submitList(selectedChars)
        }
    }



}