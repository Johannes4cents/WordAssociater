package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.PopupSearchWordBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.AdapterType
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.words.WordAdapter

fun popSearchWord(
        from: View,
        takeWordFunc : (word: Word) -> Unit,
        selectedWordsList: MutableLiveData<List<Word>>,
        showSelectAll: Boolean = false) {
    val b = PopupSearchWordBinding.inflate(LayoutInflater.from(from.context), null, false)
    var allSelected = true

    val popUp = Helper.getPopUp(b.root, from, 700, 1000)

    val popUpAdapter = WordAdapter(AdapterType.Popup, takeWordFunc, null)
    if(showSelectAll) b.selectAllLinear.visibility = View.VISIBLE

    fun selectAll() {
        val newList = selectedWordsList.value!!.toMutableList()
        for(w in newList) {
            w.selected = true
        }

        selectedWordsList.value = newList
        b.btnSelectALl.setImageResource(R.drawable.storyline_selected)
        allSelected = true
        popUpAdapter.notifyDataSetChanged()
    }

    fun deselectAll() {
        val newList = selectedWordsList.value!!.toMutableList()
        for(c in newList) {
            c.selected = false
        }

        selectedWordsList.value = newList
        b.btnSelectALl.setImageResource(R.drawable.storyline_unselected)
        allSelected = false
        popUpAdapter.notifyDataSetChanged()
    }

    b.btnSelectALl.setOnClickListener {
        if(allSelected) {
            deselectAll()
        }
        else {
            selectAll()
        }
    }

    fun setAdapter() {
        b.wordRecycler.adapter = popUpAdapter
    }
    fun setObserver() {
        b.searchBar.getWords { wordsList ->
            popUpAdapter.submitList(wordsList + selectedWordsList.value as List<Word>)
            b.wordRecycler.scrollToPosition(0)

        }

        selectedWordsList.observe(from.context as LifecycleOwner) {
            popUpAdapter.submitList(it)
            b.wordRecycler.scrollToPosition(0)

        }
    }

    setAdapter()
    setObserver()


}