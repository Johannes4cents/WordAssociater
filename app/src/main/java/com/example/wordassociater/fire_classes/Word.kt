package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.google.firebase.firestore.Exclude

data class Word(
        val text: String = "",
        val type: Type = Type.NONE,
        var id: String = "",
        var used: Int = 0,
        val snippetsList: MutableList<Long> = mutableListOf(),
        var strainsList: MutableList<Long> = mutableListOf(),
        var selected : Boolean = false,
        var lineCount : Int = 1,
        var charNumber: Long = 0,
        var imgUrl: String = "",
        var cat: Cat = Cat.General)
{
    @Exclude
    var isHeader = false

    @Exclude
    fun getStrains(): List<Strain> {
        val strains = mutableListOf<Strain>()
        for(l in strainsList) {
            var strain = Main.getStrain(l)
            if(strain != null) strains.add(strain)
        }
        return strains
    }

    @Exclude
    fun getSnippets(): List<Snippet> {
        val snippets = mutableListOf<Snippet>()
        for(l in strainsList) {
            var strain = Main.getSnippet(l)
            if(strain != null) snippets.add(strain)
        }
        return snippets
    }

    companion object {
        fun convertToIdList(wordList: List<Word>): MutableList<String> {
            val idList = mutableListOf<String>()
            for(w in wordList) idList.add(w.id)
            return idList
        }
    }
    enum class Type(val text: String) {
        Adjective("Adjective"),
        Person("Person"),
        Place("Place"),
        Action("Action"),
        Object("Object"),
        CHARACTER("Character"),
        NONE("None") }

    enum class Cat {
        General, Story, Global
    }
}