package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.PopupSearchWordBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.AdapterType
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.words.WordAdapter

fun popSearchWord(
        from: View,
        takeWordFunc : (word: Word) -> Unit,
        selectedWordsList: MutableLiveData<List<Word>>) {
    val b = PopupSearchWordBinding.inflate(LayoutInflater.from(from.context), null, false)

    val popUp = Helper.getPopUp(b.root, from, 700, 1000)

    val popUpAdapter = WordAdapter(AdapterType.Popup, takeWordFunc, null)

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