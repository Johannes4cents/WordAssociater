package com.example.wordassociater.fire_classes

import com.example.wordassociater.utils.StoryPart

class TimeLineEvent(
        override var id: Long = 0,
        override var content: String = "",
        override var characterList: MutableList<Long> = mutableListOf(),
        override var wordList: MutableList<Long> = mutableListOf(),
        override var nuwList: MutableList<Long> = mutableListOf(),
        override var storyLineList: MutableList<StoryLine> = mutableListOf(),
): StoryPart(id, content, characterList, wordList, nuwList, storyLineList) {
}