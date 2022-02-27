package com.example.wordassociater.fire_classes

import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.StoryPart

class TimeLineEvent(
        override var id: Long = 0,
        override var content: String = "",
        override var characterList: MutableList<Long> = mutableListOf(),
        override var wordList: MutableList<Long> = mutableListOf(),
        override var nuwList: MutableList<Long> = mutableListOf(),
        override var storyLineList: MutableList<Long> = mutableListOf(),
        override var date: Date = Date(0,0,1900)
): StoryPart(id, content, characterList, wordList, nuwList, storyLineList, date) {
}