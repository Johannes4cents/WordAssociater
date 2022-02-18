package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class Strain(
        override var content: String = "",
        override var wordList: MutableList<String> = mutableListOf(),
        var header: String = "Strain",
        override var id: Long = 0,
        var characterList : MutableList<Character> = mutableListOf(),
        var isStory: Boolean = false,
        var connections: Int = 0,
        var connectionLayer: Int = 1,
        var connectionsList: MutableList<Long> = mutableListOf()
): StoryPart(wordList, content, id) {

    companion object {
        fun convertToIdList(strainList: MutableList<Strain>): List<Long> {
            val idList = mutableListOf<Long>()
            for(w in strainList) idList.add(w.id)
            return idList
        }

        fun getStrainsById(idList : List<Long>): List<Strain> {
            val foundStrains = mutableListOf<Strain>()
            for(id in idList) {
                val strain = Main.strainsList.value?.find { s -> s.id == id }
                if(strain != null ) foundStrains.add(strain)
            }
            return foundStrains
        }
    }

    @Exclude
    fun getWords(): MutableList<Word> {
        val words = mutableListOf<Word>()
        for(string in wordList) {
            words.add(Main.getWord(string)!!)
        }
        return words
    }
}