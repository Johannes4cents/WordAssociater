package com.example.wordassociater.fire_classes

import com.example.wordassociater.utils.StoryPart

data class Snippet(override var content: String = "", var id: Long = 0,
                   var wordsUsed: MutableList<Word> = mutableListOf(),
                   val connectedSnippets: MutableList<Long> = mutableListOf(),
                   val characterList: MutableList<Character> = mutableListOf(),
                   var isStory: Boolean = false): StoryPart(wordsUsed, content) {
    var isHeader = false
}