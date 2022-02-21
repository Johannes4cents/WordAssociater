package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.PopupSearchWordBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.start_fragment.WordLinear
import com.example.wordassociater.utils.AdapterType
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.word.WordAdapter

fun popSearchWord(from: View, takeWordFunc : (word: Word) -> Unit, selectedWordsList: MutableLiveData<List<Word>>) {
    val b = PopupSearchWordBinding.inflate(LayoutInflater.from(from.context), null, false)
    val popUpadapter = WordAdapter(AdapterType.Popup, takeWordFunc, null)
    fun setBinding() {
        b.wordRecycler.adapter = popUpadapter

        b.searchBar.searchWords.observe(from.context as LifecycleOwner) {
            var foundWords = mutableListOf<Word>()
            for(string in it) {
                val wordList = WordLinear.allWords.filter { w -> Helper.stripWord(w.text).startsWith(string)}
                foundWords = wordList as MutableList
            }
            popUpadapter.submitList(foundWords)
        }


        selectedWordsList.observe(b.root.context as LifecycleOwner) {
            val newPopupList = WordLinear.allWords.toMutableList()
            for(word in it) {
                word.isPicked = true

                val index = newPopupList.indexOf(word)
                newPopupList.removeAt(index)
                newPopupList.add(index, word)
            }
            popUpadapter.submitList(newPopupList)



        }
    }
    setBinding()
    Helper.getPopUp(b.root, from, 600, 800)

}