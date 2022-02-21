package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.R
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
        var connectId: Long = 0,
        var imgUrl: String = "",
        var cat: Cat = Cat.General,
        var color:Int = R.color.wordBlue
)
{
    @Exclude
    var isHeader = false

    @Exclude
    var isPicked = false

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

    @Exclude
    fun getColor(type: Type): Int {
        return when(type) {
            Type.Adjective -> R.color.wordPink
            Type.Person -> R.color.wordBlue
            Type.Place -> R.color.wordBrown
            Type.Action -> R.color.green
            Type.Object -> R.color.wordPurple
            Type.CHARACTER -> R.color.wordGrey
            Type.NONE -> R.color.wordGrey
        }
    }

    @Exclude
    fun selectColor(type: Type): Int {
        return when(type) {
            Type.Adjective -> R.color.wordPink
            Type.Person -> R.color.wordBlue
            Type.Place -> R.color.wordBrown
            Type.Action -> R.color.green
            Type.Object -> R.color.wordPurple
            Type.CHARACTER -> R.color.wordGrey
            Type.NONE -> R.color.wordGrey
        }
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