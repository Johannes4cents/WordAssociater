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

fun popSearchWord(from: View, takeWordFunc : (word: Word) -> Unit, selectedWordsList: MutableLiveData<List<Word>>) {
    val b = PopupSearchWordBinding.inflate(LayoutInflater.from(from.context), null, false)

    val popUp = Helper.getPopUp(b.root, from, 600, 800)

    fun popUpTakeWordFunc(word:Word) {
        takeWordFunc(word)
    }

    val popUpAdapter = WordAdapter(AdapterType.Popup, ::popUpTakeWordFunc, null)

    fun setAdapter() {
        b.wordRecycler.adapter = popUpAdapter
        popUpAdapter.submitList(ListHelper.sortedWordList(WordLinear.allWords))
    }
    fun setObserver() {
        b.searchBar.searchWords.observe(from.context as LifecycleOwner) {
            var foundWords = mutableListOf<Word>()
            for(string in it) {
                val wordList = WordLinear.allWords.filter { w ->
                    Helper.stripWord(w.text).startsWith(string) && !foundWords.contains(w) && !selectedWordsList.value!!.contains(w)}
                foundWords = wordList as MutableList
            }
            if(selectedWordsList.value != null) {
                foundWords = (foundWords + selectedWordsList.value!!) as MutableList
            }
            Log.i("wordPopup", "searchBar.searchWords.observe")
            popUpAdapter.submitList(ListHelper.sortedWordList(foundWords))
        }

        selectedWordsList.observe(b.root.context as LifecycleOwner) {
            val newPopupList = WordLinear.allWords.toMutableList()
            for(word in it) {
                word.isPicked = true
                newPopupList.remove(word)
            }

            val submitList = (it.sortedBy { w -> w.text } + newPopupList.sortedBy { w-> w.text })
            popUpAdapter.submitList(submitList)
        }
    }

    setAdapter()
    setObserver()


}