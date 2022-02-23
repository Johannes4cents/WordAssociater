package com.example.wordassociater.popups

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.PopupSearchWordBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.AdapterType
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.ListHelper
import com.example.wordassociater.words.WordAdapter
import com.example.wordassociater.words.WordLinear

fun popSearchWord(
        from: View,
        takeWordFunc : (word: Word) -> Unit,
        selectedWordsList: MutableLiveData<MutableList<Word>>) {
    val b = PopupSearchWordBinding.inflate(LayoutInflater.from(from.context), null, false)

    val popUp = Helper.getPopUp(b.root, from, 600, 1000)

    val popUpAdapter = WordAdapter(AdapterType.Popup, takeWordFunc, null)

    fun setAdapter() {
        b.wordRecycler.adapter = popUpAdapter
        popUpAdapter.submitList(ListHelper.sortedWordList(WordLinear.allWords))
    }
    fun setObserver() {
        b.searchBar.getWords { wordsList ->
            Log.i("wordPopUp", "setObserver in popSearchWord wordsList count is ${wordsList.count()}")
            popUpAdapter.submitList(wordsList + selectedWordsList.value as List<Word>)
            b.wordRecycler.scrollToPosition(popUpAdapter.itemCount -1)
            b.wordRecycler.scrollToPosition(0)
        }

        selectedWordsList.observe(from.context as LifecycleOwner) {
            Log.i("wordPopUp", "popSearchWord / setObserver")
            popUpAdapter.submitList(it)
            b.wordRecycler.scrollToPosition(popUpAdapter.itemCount -1)
            b.wordRecycler.scrollToPosition(0)
        }
    }

    setAdapter()
    setObserver()


}