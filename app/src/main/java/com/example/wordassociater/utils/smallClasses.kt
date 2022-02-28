package com.example.wordassociater.utils

import com.example.wordassociater.Main
import com.example.wordassociater.fire_classes.Word
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
    enum class Type { Snippet, Event, Dialogue, Prose }
    @Exclude
    var isStoryPartHeader = false

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
    }
}

class CommonWord(val text: String = "",val language: Language = Language.German, val type: Type = Type.Very ) {
    enum class Type { Very, Somewhat, Uncommon}
}

class SynonymCheck(val oldWord: String = "", val newWord: String = "")

enum class Language {German, English}

enum class Drama {Conflict, Twist, Plan, Motivation, Goal, Problem, Solution, Hurdle, None, Comedy}

enum class AdapterType { List, Popup, Preview }

enum class Page {Notes, Chars, Start, Words }

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

