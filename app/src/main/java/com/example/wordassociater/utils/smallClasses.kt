package com.example.wordassociater.utils

import com.example.wordassociater.character.Character
import com.example.wordassociater.start_fragment.Word

data class Snippet( override var content: String = "", var id: Long = 0 ,
                   var wordsUsed: MutableList<Word> = mutableListOf(),
                   val connectedSnippets: MutableList<Long> = mutableListOf(),
                   val characterList: MutableList<Character> = mutableListOf(),
                   var isStory: Boolean = false): StoryPart(wordsUsed, content) {
    var isHeader = false
}

data class Stats(
        var charNumber: Long = 0,
        var snippetNumber: Long = 0,
        var noteNumber: Long = 0,
        var wordNumber: Long = 0,
        var strainNumber: Long = 0)

open class StoryPart(var words: MutableList<Word>, open var content: String)