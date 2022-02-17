package com.example.wordassociater.strain_edit_fragment

import com.example.wordassociater.character.Character
import com.example.wordassociater.start_fragment.Word
import com.example.wordassociater.utils.StoryPart

data class Strain(
        override var content: String = "",
        var wordList: MutableList<Word> = mutableListOf(),
        var header: String = "Strain",
        var id: String = "",
        var characterList : MutableList<Character> = mutableListOf(),
        var isStory: Boolean = false,
        var connections: Int = 0,
        var connectionLayer: Int = 1,
        var connectionsList: MutableList<String> = mutableListOf()
): StoryPart(wordList, content)