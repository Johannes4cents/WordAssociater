package com.example.wordassociater.utils

import com.example.wordassociater.fire_classes.Word

object ListHelper {

    fun sortedWordList(list: List<Word>): List<Word> {
        return list.sortedBy { w ->
            w.text
        }
    }
}