package com.example.wordassociater.fire_classes

import com.example.wordassociater.utils.StoryPart

data class Prose(
        override var id: Long = 0,
        override var content: String = "",
        var header: String = "",
        override var wordList: MutableList<String> = mutableListOf(),
        override var characterList: MutableList<Long> = mutableListOf(),
        override var nuwList: MutableList<Nuw> = mutableListOf()
        ): StoryPart(id, content, wordList, characterList, nuwList) {
}