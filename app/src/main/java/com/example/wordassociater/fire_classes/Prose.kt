package com.example.wordassociater.fire_classes

import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.StoryPart

data class Prose(
        override var id: Long = 0,
        override var content: String = "",
        var header: String = "",
        override var wordList: MutableList<Long> = mutableListOf(0),
        override var characterList: MutableList<Long> = mutableListOf(22),
        override var nuwList: MutableList<Long> = mutableListOf(),
        override var storyLineList: MutableList<Long> = mutableListOf(),
        override var date: Date = Date(),
        override var type: Type = Type.Prose
        ): StoryPart(id, content, wordList, characterList, nuwList, storyLineList, date, type) {
}