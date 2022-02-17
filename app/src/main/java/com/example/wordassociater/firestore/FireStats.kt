package com.example.wordassociater.firestore

object FireStats {
    fun getStrainNumber(): Long {
        val number = FireLists.stats!!.strainNumber + 1
        FireLists.fireStats.update("strainNumber", number)
        return number
    }

    fun getCharNumber() : Long {
        val number = FireLists.stats!!.charNumber + 1
        FireLists.fireStats.update("charNumber", number)
        return number
    }

    fun getSnippetNumber(): Long {
        val number = FireLists.stats!!.snippetNumber + 1
        FireLists.fireStats.update("snippetNumber", number)
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