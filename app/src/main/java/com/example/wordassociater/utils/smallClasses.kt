package com.example.wordassociater.utils

import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.fire_classes.Nuw
import com.example.wordassociater.fire_classes.Word
import com.google.firebase.firestore.Exclude

open class StoryPart(
        open var id : Long,
        open var content: String,
        open var wordList: MutableList<String>,
        open var characterList: MutableList<Long>,
        open var nuwList: MutableList<Nuw>) {

    @Exclude
    fun getWords(): MutableList<Word> {
        val words = mutableListOf<Word>()
        for(string in wordList) {
            val found = Main.getWord(string)
            if(found != null) words.add(found)
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

enum class WordColor(path: Int)
{
    Blue(R.color.wordBlue),
    Pink(R.color.wordPink),
    Brown(R.color.wordBrown),
    Green(R.color.wordGreen),
    Grey(R.color.wordGrey),
    Purple(R.color.wordPurple)}

enum class AdapterType { List, Popup, Preview }

enum class Page {Chars, Start, Words }

data class ConnectedWord(val word: Word, var amount: Int) {

    companion object {
        fun addWord(word: Word, list: MutableList<ConnectedWord>): MutableList<ConnectedWord> {
            val newList = mutableListOf<ConnectedWord>()
            for(connectedWord in list) {
                if(connectedWord.word.id == word.id) {
                    connectedWord.amount += 1
                    newList.add(connectedWord)
                }
                else {
                    newList.add(ConnectedWord(word, 1))
                }
            }
            return newList
        }
    }
}

