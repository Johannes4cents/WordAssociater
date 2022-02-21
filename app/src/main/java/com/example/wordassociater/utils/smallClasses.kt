package com.example.wordassociater.utils

import com.example.wordassociater.R

open class StoryPart(open var wordList: MutableList<String>, open var content: String, open var id : Long) {

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

