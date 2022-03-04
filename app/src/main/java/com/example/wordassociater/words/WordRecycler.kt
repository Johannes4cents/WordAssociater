package com.example.wordassociater.words

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Word

class WordRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    enum class Mode { Preview, Popup, List }
    private lateinit var liveList: MutableLiveData<List<Word>>
    private lateinit var mode: Mode
    lateinit var wordAdapter: WordAdapter

    fun initRecycler(mode: Mode, liveList: MutableLiveData<List<Word>>?, onWordSelected: ((word: Word) -> Unit)? ) {
        this.mode = mode
        this.liveList = liveList ?: MutableLiveData<List<Word>>()
        wordAdapter = WordAdapter(mode, onWordSelected)
        layoutManager = if(mode == Mode.Preview) LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) else LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = wordAdapter
        setObserver()
    }

    fun setLiveList(list: List<Word>) {
        liveList.value = list
    }

    private fun setObserver() {
        liveList.observe(context as LifecycleOwner) {
            val selectedWords = it.filter { w -> w.id != 0L  }.filter { w -> w.selected }.sortedBy { w -> w.text }.sortedBy { w -> w.selected }
            val allWords = it.filter { w -> w.id != 0L  }.sortedBy { w -> w.text }.sortedBy { w -> w.selected }.sortedBy { w -> w.isAHeader }

            wordAdapter.submitList(if(mode == Mode.Preview) selectedWords else allWords)
        }
    }
}