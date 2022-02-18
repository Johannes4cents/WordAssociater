package com.example.wordassociater.utils

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

