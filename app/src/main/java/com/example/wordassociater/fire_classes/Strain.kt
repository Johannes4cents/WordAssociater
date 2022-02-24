package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireStrains
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class Strain(
        override var content: String = "",
        override var wordList: MutableList<String> = mutableListOf(),
        var header: String = "Strain",
        override var id: Long = 0,
        override var characterList : MutableList<Long> = mutableListOf(),
        var isStory: Boolean = false,
        var connections: Int = 0,
        var connectionLayer: Int = 1,
        var connectionsList: MutableList<Long> = mutableListOf(),
        var drama: Drama.Type = Drama.Type.None,
        override var nuwList: MutableList<Nuw> = mutableListOf()
): StoryPart(id, content, wordList, characterList, nuwList) {

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

    @Exclude fun getConnections(): List<Strain> {
        val strainsies = mutableListOf<Strain>()
        val toRemoveList = mutableListOf<Long>()
        for(id in connectionsList) {
            val strain = Main.getStrain(id)
            if(strain != null)  strainsies.add(strain)
            else toRemoveList.add(id)
        }
        for(id in toRemoveList) {
            connectionsList.remove(id)
        }
        return strainsies

    }

    @Exclude
    fun getCharacters(): List<Character> {
        val chars = mutableListOf<Character>()
        for(id in characterList) {
            val char = Main.characterList.value?.find { c -> c.id == id }
            if(char != null) chars.add(char)
        }
        return chars
    }

    @Exclude
    fun delete() {
        for(w in getWords()) {
            w.used -= 1
            w.strainsList.remove(id)
            FireWords.update(w.type, w.id, "used", w.used)
            FireWords.update(w.type, w.id, "strainsList", w.used)
        }

        for(c in getCharacters()) {
            c.strainsList.remove(id)
            FireChars.update(c.id, "strainsList", c.strainsList)
        }

        for(strain in getConnections()) {
            strain.connectionsList.remove(id)
            FireSnippets.update(strain.id, "connectionsList", strain.connectionsList)
        }

        FireStrains.delete(id)
    }
}