package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.firestore.*
import com.google.firebase.firestore.Exclude

data class Word(
        var text: String = "",
        var type: Type = Type.NONE,
        var id: String = "",
        var used: Int = 0,
        val snippetsList: MutableList<Long> = mutableListOf(),
        var strainsList: MutableList<Long> = mutableListOf(),
        var dialogueList: MutableList<Long> = mutableListOf(),
        var selected : Boolean = false,
        var lineCount : Int = 1,
        var connectId: Long = 0,
        var imgUrl: String = "",
        var cat: Cat = Cat.General,
        var color:Int = R.color.wordBlue,
        var spheres: MutableList<Long> = mutableListOf(1),
        var wordCatsList: MutableList<Long> = mutableListOf(1),
        var branchOf: String = "",
        var synonyms: MutableList<String> = mutableListOf(),
        var rootOf: MutableList<String> = mutableListOf(),
        var wordConnectionsList: MutableList<Long> = mutableListOf()
)
{
    @Exclude
    var isHeader = false

    @Exclude
    var isPicked = false

    @Exclude
    fun getStrains(): List<Strain> {
        val strains = mutableListOf<Strain>()
        val toRemoveIds = mutableListOf<Long>()
        for(l in strainsList) {
            var strain = Main.getStrain(l)
            if(strain != null) strains.add(strain)
            else toRemoveIds.add(l)
        }
        for(id in toRemoveIds) {
            strainsList.remove(id)
            FireWords.update(type, this.id, "strainsList", strainsList)
        }
        return strains
    }

    @Exclude
    fun getSnippets(): List<Snippet> {
        val snippets = mutableListOf<Snippet>()
        val toRemoveIds = mutableListOf<Long>()
        for(l in strainsList) {
            var strain = Main.getSnippet(l)
            if(strain != null) snippets.add(strain)
            else toRemoveIds.add(l)
        }
        for(id in toRemoveIds) {
            snippetsList.remove(id)
            FireWords.update(type, this.id, "snippetsList", snippetsList)
        }
        return snippets
    }

    @Exclude
    fun getDialogues(): List<Dialogue>{
        val dials = mutableListOf<Dialogue>()
        val toRemoveIds = mutableListOf<Long>()
        for (l in dialogueList) {
            val dialogue = Main.getDialogue(l)
            if(dialogue != null) dials.add(dialogue)
            else toRemoveIds.add(l)
        }

        for(id in toRemoveIds) {
            dialogueList.remove(id)
            FireWords.update(type, this.id, "dialogueList", dialogueList)
        }
        return dials
    }


    @Exclude
    fun getWordConnections(): List<WordConnection> {
        val wcs = mutableListOf<WordConnection>()
        val toRemoveIds = mutableListOf<Long>()
        for(id in wordConnectionsList) {
            val wc = Main.getWordConnection(id)
            if(wc != null) wcs.add(wc)
            else toRemoveIds.add(id)
        }

        for(id in toRemoveIds) {
            wordConnectionsList.remove(id)
            FireWords.update(type, this.id, "connections", wordConnectionsList)
        }

        return wcs
    }

    @Exclude
    fun getSphereList(): List<Sphere> {
        val sphereList = mutableListOf<Sphere>()
        for(sphere in spheres) {
            val sphere = Main.getSphere(sphere)
            if (sphere != null) {
                sphereList.add(sphere)
            }
        }
        return sphereList
    }

    @Exclude
    fun getTypeInitials(): String {
        return when(type) {
            Word.Type.Adjective -> "Adj"
            Word.Type.Person -> "Per"
            Word.Type.Place -> "Pla"
            Word.Type.Action -> "Act"
            Word.Type.Object -> "Obj"
            Word.Type.CHARACTER -> "Cha"
            Word.Type.NONE -> "Non"
        }
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

    @Exclude
    fun delete() {
        for(snippet in getSnippets()) {
            snippet.wordList.remove(id)
            FireSnippets.update(snippet.id, "wordList", snippet.wordList)
        }

        for(strain in getStrains()) {
            strain.wordList.remove(id)
            FireStrains.update(strain.id, "wordList", strain.wordList)
        }

        for(dialogue in getDialogues()) {
            dialogue.wordList.remove(id)
            FireDialogue.update(dialogue.id, "wordList", dialogue.wordList)
        }

        val rootWord = Main.getWord(branchOf)
        if(rootWord != null) {
            rootWord.rootOf.remove(id)
            FireWords.update(rootWord.type, rootWord.id, "roofOf", rootWord.rootOf)
        }

        for(w in convertIdListToWord(synonyms)) {
            w.synonyms.remove(id)
            FireWords.update(w.type,w.id, "synonyms", w.synonyms)
        }

        for(w in convertIdListToWord(rootOf)) {
            w.branchOf = ""
            FireWords.update(w.type, w.id, "branchOf", w.branchOf)
        }

        for(wc in getWordConnections()) {
            val word = Main.getWord(wc.word)
            if(word != null) {
                word.wordConnectionsList.remove(wc.id)
                FireWords.update(word.type,word.id, "wordConnectionsList", word.wordConnectionsList)
            }
            FireWordConnections.delete(wc.id)
        }

        FireWords.delete(type, id)

    }



    companion object {
        fun convertToIdList(wordList: List<Word>): MutableList<String> {
            val idList = mutableListOf<String>()
            for(w in wordList) idList.add(w.id)
            return idList
        }

        fun convertIdListToWord(idList: List<String>): MutableList<Word> {
            val wordList = mutableListOf<Word>()
            for(w in idList) Main.getWord(w)?.let { wordList.add(it) }
            return wordList
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