package com.example.wordassociater.fire_classes

import android.util.Log
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.*
import com.google.firebase.firestore.Exclude
import java.util.*

data class Word(
        var text: String = "",
        var cats: MutableList<Long> = mutableListOf(0),
        var id: Long = 0,
        var used: Int = 0,
        var snippetsList: MutableList<Long> = mutableListOf(),
        var dialogueList: MutableList<Long> = mutableListOf(),
        var eventList: MutableList<Long> = mutableListOf(),
        var proseList: MutableList<Long> = mutableListOf(),
        var connectId: Long = 0,
        var imgUrl: String = "",
        var famList: MutableList<Long> = mutableListOf(),
        var spheres: MutableList<Long> = mutableListOf(2),
        var wordConnectionsList: MutableList<Long> = mutableListOf(),
        var stems: MutableList<String> = mutableListOf()
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
    fun getFamStrings(): List<String> {
        val stringList = mutableListOf<String>()
        for(fam in getFams()) {
            stringList.add(fam.text)
        }
        return stringList
    }

    @Exclude
    fun getMainFam(): Fam? {
        return getFams().find { f -> f.main }
    }

    @Exclude
    fun getFams(): List<Fam> {
        val fams = mutableListOf<Fam>()
        val toRemoveIds = mutableListOf<Long>()
        for(id in famList) {
            val fam = Main.getFam(id)
            if(fam != null) {
                fams.add(fam)
            }
            else toRemoveIds.add(id)
        }

        for(id in toRemoveIds) {
            famList.remove(id)
            FireWords.update(this.id, "famList", famList)
        }
        return fams
    }

    @Exclude
    fun getSnippets(): List<Snippet> {
        val snippets = mutableListOf<Snippet>()
        val toRemoveIds = mutableListOf<Long>()
        for(l in snippetsList) {
            var snippet = Main.getSnippet(l)
            if(snippet != null) snippets.add(snippet)
            else toRemoveIds.add(l)
        }
        for(id in toRemoveIds) {
            snippetsList.remove(id)
            FireWords.update(this.id, "snippetsList", snippetsList)
        }
        return snippets
    }

    fun checkIfFamExists(name: String): Fam? {
        return getFams().find { f -> f.text.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT) == name.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT) }
    }

    fun getEvents(): List<Event> {
        val events = mutableListOf<Event>()
        val toRemoveIds = mutableListOf<Long>()
        for(l in eventList) {
            var event = Main.getEvent(l)
            if(event != null) events.add(event)
            else toRemoveIds.add(l)
        }
        for(id in toRemoveIds) {
            eventList.remove(id)
            FireEvents.update(this.id, "eventList", eventList)
        }
        return events
    }

    @Exclude
    fun getProse(): List<Prose> {
        val pList = mutableListOf<Prose>()
        val toRemoveIds = mutableListOf<Long>()
        for(l in proseList) {
            var prose = Main.getProse(l)
            if(prose != null) pList.add(prose)
            else toRemoveIds.add(l)
        }
        for(id in toRemoveIds) {
            proseList.remove(id)
            FireWords.update(this.id, "proseList", snippetsList)
        }
        return pList
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

        for(dialogue in getDialogues()) {
            dialogue.wordList.remove(id)
            FireDialogue.update(dialogue.id, "wordList", dialogue.wordList)
        }

        for(wc in getWordConnections()) {
            val words = Word.convertIdListToWord(wc.wordsList)
            for(w in words){
                w.wordConnectionsList.remove(wc.id)
                FireWords.update(w.id, "wordConnectionsList", w.wordConnectionsList)
                FireWordConnections.delete(wc.id)
            }

        }

        for(event in getEvents()) {
            event.wordList.remove(id)
            FireEvents.update(event.id, "wordList", event.wordList)
        }

        for(prose in getProse()) {
            prose.wordList.remove(id)
            FireProse.update(prose.id, "wordList", prose.wordList)
        }

        for(fam in getFams()) {
            fam.delete()
        }

        FireWords.delete(id)

    }

    fun copyMe(): Word {
        val copy = Word()
        copy.text = text
        copy.cats = cats.toMutableList()
        copy.id = id
        copy.used = used
        copy.snippetsList = snippetsList.toMutableList()
        copy.dialogueList = dialogueList.toMutableList()
        copy.eventList = eventList.toMutableList()
        copy.proseList = proseList.toMutableList()
        copy.connectId = connectId
        copy.imgUrl = imgUrl
        copy.famList = famList.toMutableList()
        copy.spheres = spheres.toMutableList()
        copy.wordConnectionsList = wordConnectionsList.toMutableList()
        copy.stems = stems.toMutableList()

        return copy
    }



    companion object {
        val any = Word(id = 0, text = "Any")

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