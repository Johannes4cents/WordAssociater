package com.example.wordassociater.utils

import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.FireLists
import com.google.firebase.firestore.Exclude

open class StoryPart(
        open var id : Long,
        open var content: String,
        open var wordList: MutableList<Long>,
        open var characterList: MutableList<Long>,
        open var nuwList: MutableList<Long>,
        open var storyLineList: MutableList<Long>,
        open var date: Date,
        open var type: Type
        ) {
    enum class Type { Snippet, Event, Prose, Header }
    @Exclude
    var isStoryPartHeader = false

    @Exclude
    var index = 0

    @Exclude
    fun getWordsAsStory(): MutableList<Word> {
        val words = mutableListOf<Word>()
        val notFound = mutableListOf<Long>()
        for(id in wordList) {
            val found = Main.getWord(id)
            if(found != null) words.add(found)
            else notFound.add(id)
        }
        return words
    }


    companion object {
        fun getIdList(wordsList: MutableList<StoryPart>): List<Long> {
            var idList = mutableListOf<Long>()
            for(w in wordsList) {
                idList.add(w.id)
            }
            return idList
        }
        fun getListFromIds(wordsList: List<Long>): List<StoryPart> {
            var storyParts = mutableListOf<StoryPart>()
            for(id in wordsList) {
                val sp = Main.getStoryPart(id)
                if(sp != null) storyParts.add(sp)
            }
            return storyParts
        }
    }
}


class CommonWord(val text: String = "",val language: Language = Language.German, val type: Type = Type.Very ) {
    enum class Type { Very, Somewhat, Uncommon}

    companion object {
        fun getTypeImage(type: Type): Int {
            return when(type) {
                Type.Very -> R.drawable.common_type_very
                Type.Somewhat -> R.drawable.common_type_somewhat
                Type.Uncommon -> R.drawable.icon_word
            }
        }
    }
}


class FamCheck(val stem: String = "", val newFam: String = "") {

    companion object {
        fun addFamCheck(stem: String, newFam: String) {
            val famCheck = FamCheck(stem, newFam)
            FireLists.addNewFamCheck(famCheck)
        }
    }

}

enum class Language {German, English}

enum class Drama {Conflict, Twist, Plan, Motivation, Goal, Problem, Solution, Hurdle, None, Comedy}

enum class AdapterType { List, Popup, Preview }

enum class Page {Notes, Items, Events, Locations, Chars, Start, Words, Nuws }

data class ConnectedWord(val word: Word, var amount: Int) {

    companion object {
        fun addWord(word: Word, list: MutableList<ConnectedWord>): MutableList<ConnectedWord> {
            val newList = mutableListOf<ConnectedWord>()
            if(list.isNotEmpty()) {
                for(connectedWord in list) {
                    if(connectedWord.word.id == word.id) {
                        connectedWord.amount += 1
                        newList.add(connectedWord)
                    }
                    else {
                        newList.add(ConnectedWord(word, 1))
                    }
                }
            }
            else {
                newList.add(ConnectedWord(word, 1))
            }
            return newList
        }
    }
}

