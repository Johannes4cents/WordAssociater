package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.PopupSearchWordBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.words.WordRecycler

fun popSearchWord(
        from: View,
        takeWordFunc : (word: Word) -> Unit,
        selectedWordsList: MutableLiveData<List<Word>>,
        onHeaderClicked: ((wordText: String) -> Unit)?,
        showSelectAll: Boolean = false) {
    val b = PopupSearchWordBinding.inflate(LayoutInflater.from(from.context), null, false)
    var allSelected = true

    val popUp = Helper.getPopUp(b.root, from, 700, 1000)


    b.wordRecycler.initRecycler(WordRecycler.Mode.Popup, selectedWordsList, takeWordFunc, onHeaderClicked)
    b.selectAllLinear.visibility = if(showSelectAll) View.VISIBLE else View.GONE
    b.searchBar.setTextColorToWhite()

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
        b.searchBar.getWords(selectedWordsList.value!!) { wordsList ->
            if(wordsList.isNotEmpty()) selectedWordsList.value = (wordsList)
            else
            b.wordRecycler.scrollToPosition(0)

        }
    }

    setObserver()


}