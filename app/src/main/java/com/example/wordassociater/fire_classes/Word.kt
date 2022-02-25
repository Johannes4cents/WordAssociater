package com.example.wordassociater.fire_classes

import android.util.Log
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.*
import com.google.firebase.firestore.Exclude

data class Word(
        var text: String = "",
        var cats: MutableList<Long> = mutableListOf(),
        var id: Long = 0,
        var used: Int = 0,
        var snippetsList: MutableList<Long> = mutableListOf(),
        var strainsList: MutableList<Long> = mutableListOf(),
        var dialogueList: MutableList<Long> = mutableListOf(),
        var connectId: Long = 0,
        var imgUrl: String = "",
        var spheres: MutableList<Long> = mutableListOf(1),
        var branchOf: String = "",
        var synonyms: MutableList<String> = mutableListOf(),
        var rootOf: MutableList<Long> = mutableListOf(),
        var wordConnectionsList: MutableList<Long> = mutableListOf()
)
{
    @Exclude
    var isHeader = false

    @Exclude
    var isPicked = false

    @Exclude
    var selected : Boolean = false

    @Exclude
    fun getCatsList(): List<WordCat> {
        val catsList = mutableListOf<WordCat>()
        for(id in cats) {
            val cat = Main.getWordCat(id)
            if(cat != null) catsList.add(cat)
        }

        return catsList
    }

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
            FireWords.update(this.id, "strainsList", strainsList)
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
            FireWords.update(this.id, "snippetsList", snippetsList)
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
            FireWords.update(this.id, "dialogueList", dialogueList)
        }
        return dials
    }


    @Exclude
    fun getWordConnections(): List<WordConnection> {
        val wcs = mutableListOf<WordConnection>()
        val toRemoveIds = mutableListOf<Long>()
        for(id in wordConnectionsList) {
            val wc = Main.getWordConnection(id)
            Log.i("deselectTest", "word.getWordTwoConnections | wc found is: $wc")
            if(wc != null) wcs.add(wc)
            else toRemoveIds.add(id)
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
    fun increaseWordUsed() {
        used += 1
        FireWords.update(id, "used" , used)
    }

    @Exclude
    fun decreaseWordUsed() {
        used -= 1
        FireWords.update(id, "used", used)
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

        val rootWord = Main.getWord(branchOf.toLong())
        if(rootWord != null) {
            rootWord.rootOf.remove(id)
            FireWords.update(rootWord.id, "roofOf", rootWord.rootOf)
        }

        for(w in convertIdListToWord(rootOf)) {
            w.branchOf = rootOf.toString()
            FireWords.update(w.id, "branchOf", w.branchOf)
        }

        for(wc in getWordConnections()) {
            val words = Word.convertIdListToWord(wc.wordsList)
            for(w in words){
                w.wordConnectionsList.remove(wc.id)
                FireWords.update(w.id, "wordConnectionsList", w.wordConnectionsList)
                FireWordConnections.delete(wc.id)
            }

        }
        FireWords.delete(id)

    }



    companion object {
        fun convertToIdList(wordList: List<Word>): MutableList<Long> {
            val idList = mutableListOf<Long>()
            for(w in wordList) idList.add(w.id)
            return idList
        }

        fun convertIdListToWord(idList: List<Long>): MutableList<Word> {
            val wordList = mutableListOf<Word>()
            for(w in idList) Main.getWord(w)?.let { wordList.add(it) }
            return wordList
        }

        fun fireGet(id: Long, onWordReceived: (word: Word) -> Unit) {
            FireLists.wordsList.document(id.toString()).get().addOnSuccessListener {
                val word = it.toObject(Word::class.java)
                if(word != null) onWordReceived(word)

            }
        }

    }

}