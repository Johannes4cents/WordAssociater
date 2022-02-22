package com.example.wordassociater.fire_classes

import com.example.wordassociater.utils.StoryPart

class Dramaturgy(
        override var id: Long = 0,
        override var content: String = "",
        override var wordList: MutableList<String> = mutableListOf(),
        var characterList: MutableList<Long> = mutableListOf(),
        var type: Type = Type.Conflict
): StoryPart(wordList, content, id) {
    enum class Type {Conflict, Twist, Plan, Motivation, Goal, Problem, Solution, Hurdle}
}