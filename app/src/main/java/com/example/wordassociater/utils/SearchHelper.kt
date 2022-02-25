package com.example.wordassociater.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.fire_classes.Word

object SearchHelper {
    fun setWordList(selectedWordList: List<Word>, liveWordList: MutableLiveData<MutableList<Word>>) {
        val wordList = Main.wordsList.value!!.toMutableList()
        for(w in wordList) {
            w.selected = selectedWordList.contains(w)
        }
        Log.i("wordList", "selectedWordList is $selectedWordList")

        val newList = selectedWordList + wordList.sortedBy { w -> w.text }.sortedBy { w -> w.selected }.toMutableList()
        liveWordList.value = newList.toMutableList()
    }


}