package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.PopupSearchWordBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.words.WordRecycler

fun popSearchWord(
        from: View,
        takeWordFunc : (word: Word) -> Unit,
        selectedWordsList: MutableLiveData<List<Word>>,
        showSelectAll: Boolean = false) {
    val b = PopupSearchWordBinding.inflate(LayoutInflater.from(from.context), null, false)
    var allSelected = true

    val popUp = Helper.getPopUp(b.root, from, 700, 1000)

    b.wordRecycler.initRecycler(WordRecycler.Mode.Popup, selectedWordsList, takeWordFunc)
    if(showSelectAll) b.selectAllLinear.visibility = View.VISIBLE

    fun selectAll() {
        val newList = selectedWordsList.value!!.toMutableList()
        for(w in newList) {
            w.selected = true
        }

        selectedWordsList.value = newList
        b.btnSelectALl.setImageResource(R.drawable.storyline_selected)
        allSelected = true
        b.wordRecycler.wordAdapter.notifyDataSetChanged()
    }

    fun deselectAll() {
        val newList = selectedWordsList.value!!.toMutableList()
        for(c in newList) {
            c.selected = false
        }

        selectedWordsList.value = newList
        b.btnSelectALl.setImageResource(R.drawable.storyline_unselected)
        allSelected = false
        b.wordRecycler.wordAdapter.notifyDataSetChanged()
    }

    b.btnSelectALl.setOnClickListener {
        if(allSelected) {
            deselectAll()
        }
        else {
            selectAll()
        }
    }

    fun setObserver() {
        b.searchBar.getWords(Main.wordsList.value!!) { wordsList ->
            selectedWordsList.value = (wordsList + selectedWordsList.value as List<Word>)
            b.wordRecycler.scrollToPosition(0)

        }
    }

    setObserver()


}