package com.example.wordassociater.firestore

object FireStats {
    fun getStoryPartNumber(): Long {
        val number = FireLists.stats!!.storyPartNumber + 1
        FireLists.fireStats.update("storyPartNumber", number)
        return number
    }

    fun getCharNumber() : Long {
        val number = FireLists.stats!!.charNumber + 1
        FireLists.fireStats.update("charNumber", number)
        return number
    }

    fun getNoteNumber(): Long {
        val number = FireLists.stats!!.noteNumber + 1
        FireLists.fireStats.update("noteNumber", number)
        return number
    }

    fun getWordNumber(): Long {
        val number = FireLists.stats!!.wordNumber + 1
        FireLists.fireStats.update("wordNumber", number)
        return number
    }
}