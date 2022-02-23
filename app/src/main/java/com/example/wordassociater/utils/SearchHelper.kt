package com.example.wordassociater.utils

import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.words.WordLinear

object SearchHelper {

    fun setWordList(selectedWordList: List<Word>, liveWordList: MutableLiveData<MutableList<Word>>) {
        val wordList = WordLinear.allWords.toMutableList()
        for(w in wordList) {
            w.selected = selectedWordList.contains(w)
        }

        val newList = selectedWordList + wordList.sortedBy { w -> w.text }.sortedBy { w -> w.selected }.toMutableList()
        liveWordList.value = newList.toMutableList()
    }


}