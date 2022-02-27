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
    enum class Type { List, Popup }
    lateinit var synonymAdapter: SynonymAdapter
    lateinit var synonymList: MutableLiveData<List<String>>


    fun initRecycler(type: Type, word: Word, synonymList: MutableLiveData<List<String>>, onHeaderClicked: () -> Unit, takeContentFunc: (text: String) -> Unit) {
        synonymAdapter = SynonymAdapter(type, synonymList, word, onHeaderClicked, takeContentFunc)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.synonymList = synonymList
        adapter = synonymAdapter
        val callback = SwipeToDeleteCallback(synonymAdapter)
        ItemTouchHelper(callback).attachToRecyclerView(this)
        setObserver()
    }

    fun setObserver() {
        synonymList.observe(context as LifecycleOwner) {
            val newList = it.toMutableSet()
            newList.remove("synonymHeader")
            newList.remove("SynonymHeader")
            synonymAdapter.submitList(newList.sorted().reversed() + listOf("synonymHeader"))
            smoothScrollToPosition(synonymAdapter.currentList.count() - 1)
        }
    }


}