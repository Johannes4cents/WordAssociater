package com.example.wordassociater.synonyms

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.SwipeToDeleteCallback

class SynonymRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    lateinit var synonymAdapter: SynonymAdapter
    lateinit var synonymList: MutableLiveData<List<String>>


    fun initRecycler(word: Word, synonymList: MutableLiveData<List<String>>, onHeaderClicked: () -> Unit, takeContentFunc: (text: String) -> Unit) {
        synonymAdapter = SynonymAdapter(synonymList, word, onHeaderClicked, takeContentFunc)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.synonymList = synonymList
        adapter = synonymAdapter
        val callback = SwipeToDeleteCallback(synonymAdapter)
        ItemTouchHelper(callback).attachToRecyclerView(this)
        setObserver()
    }

    fun setObserver() {
        synonymList.observe(context as LifecycleOwner) {
            it.toMutableList().remove("synonymHeader")
            if(it != null) synonymAdapter.submitList(it.sorted().reversed() + listOf("synonymHeader"))
            else synonymAdapter.submitList(listOf("synonymHeader"))
            smoothScrollToPosition(synonymAdapter.currentList.count() - 1)
        }
    }


}