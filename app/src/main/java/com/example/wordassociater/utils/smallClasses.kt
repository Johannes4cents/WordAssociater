package com.example.wordassociater.utils

import com.example.wordassociater.R
import com.example.wordassociater.fire_classes.Nuw

open class StoryPart(
        open var id : Long,
        open var content: String,
        open var wordList: MutableList<String>,
        open var characterList: MutableList<Long>,
        open var nuwList: MutableList<Nuw>) {

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

enum class AdapterType { List, Popup }

enum class Page(number: Int) {Chars(0), Start(1), Words(2)}

data class WordConnection(var word: String = "", var connected: Int = 0)
