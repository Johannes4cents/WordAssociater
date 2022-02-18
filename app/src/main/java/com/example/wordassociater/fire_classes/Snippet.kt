package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class Snippet(override var content: String = "", override var id: Long = 0,
                   override var wordList: MutableList<String> = mutableListOf(),
                   val connectedSnippets: MutableList<Long> = mutableListOf(),
                   val characterList: MutableList<Character> = mutableListOf(),
                   var isStory: Boolean = false): StoryPart(wordList, content, id) {
    @Exclude
    var isHeader = false
    @Exclude
    fun getWords(): MutableList<Word> {
        val words = mutableListOf<Word>()
        for(string in wordList) {
            words.add(Main.getWord(string)!!)
        }
        return words
    }

}