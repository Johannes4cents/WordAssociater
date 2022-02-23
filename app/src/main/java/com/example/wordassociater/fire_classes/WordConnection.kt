package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.google.firebase.firestore.Exclude

data class WordConnection(
        var id: Long = 0,
        var connectedWords: MutableList<String> = mutableListOf(),
        var storyPart: Long = 0) {

    @Exclude
    fun getWords(): List<Word> {
        val wordList = mutableListOf<Word>()
        for(string in connectedWords) {
            val word = Main.getWord(string)
            if(word != null) wordList.add(word)
        }
        return wordList
    }

    companion object {
        val idList = mutableListOf<Long>()

        fun getId(): Long {
            var id: Long = 1
            while(idList.contains(id)) {
                id = (0..100000000).random().toLong()
            }
            return id
        }
    }
}