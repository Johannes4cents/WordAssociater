package com.example.wordassociater.fire_classes

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.*
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Image
import com.example.wordassociater.utils.LiveClass
import com.google.firebase.firestore.Exclude
import java.util.*

data class Word(
        var text: String = "",
        var cats: MutableList<Long> = mutableListOf(0),
        override var id: Long = 0,
        var used: Int = 0,
        var snippetsList: MutableList<Long> = mutableListOf(),
        var eventList: MutableList<Long> = mutableListOf(),
        var storyLineList: MutableList<Long> = mutableListOf(),
        var connectId: Long = 0,
        var imgUrl: String = "",
        var famList: MutableList<Long> = mutableListOf(),
        var spheres: MutableList<Long> = mutableListOf(2),
        var wordConnectionsList: MutableList<Long> = mutableListOf(),
        var stems: MutableList<String> = mutableListOf(),
        var type: Type = Type.Other,
): LiveClass
{
    enum class Type { Character, Item, Location, Event, Other }

    @get:Exclude
    val liveWordCats= MutableLiveData<List<LiveClass>>()

    @Exclude
    override var name: String = text

    override var image: Long = 0L

    @Exclude
    override var sortingOrder: Int = used

    @Exclude
    override var isAHeader = false

    @Exclude
    var isPicked = false

    @Exclude
    override var selected : Boolean = false

    @Exclude
    var adapterPosition = 0

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
    fun getFullCatsList() {
        if(connectId == 0L) {
            val catsList = Main.wordCatsList.value!!.toMutableList().filter { wc ->
                wc.id != 0L && wc.id != 1L && wc.id != 2L && wc.id != 3L && wc.id != 4L}
            for(cat in catsList) {
                cat.selected = cats.contains(cat.id)
            }
            liveWordCats.value = catsList
        }
    }

    @Exclude
    fun takeWordCat(wordCat: WordCat) {
        if(cats.contains(wordCat.id)) {
            cats.remove(wordCat.id)
            val wc = Main.getWordCat(wordCat.id)!!
            wc.wordList.remove(id)
            FireWordCats.update(wc.id, "wordList", wc.wordList)
        }
        else {
            cats.add(wordCat.id)
            val wc = Main.getWordCat(wordCat.id)!!
            wc.wordList.add(id)
            FireWordCats.update(wc.id, "wordList", wc.wordList)
        }

        FireWords.update(id, "cats", cats)
        getFullCatsList()
    }

    @Exclude
    fun getStoryLines(): List<StoryLine> {
        val list = mutableListOf<StoryLine>()
        for(id in storyLineList) {
            val storyLine = Main.getStoryLine(id)
            if(storyLine != null) list.add(storyLine)
        }
        return  list
    }

    @Exclude
    fun getImage(): Image {
        return Image.getDrawable(image)
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
    fun getWordConnections(): List<WordConnection> {
        val wcs = mutableListOf<WordConnection>()
        val toRemoveIds = mutableListOf<Long>()
        for(id in wordConnectionsList) {
            val wc = Main.getWordConnection(id)
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
        copy.eventList = eventList.toMutableList()
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

        fun create(context: Context,
                   text: String,
                   cats: List<Long> = listOf(),
                   snippetsList: List<Long> = listOf(),
                   eventList: List<Long> = listOf(),
                   proseList: List<Long> = listOf(),
                   connectId: Long = 0,
                   imgUrl: String = "",
                   spheres: List<Long> = listOf() ): Word? {
            var newWord: Word? = Word()
            newWord!!.id = FireStats.getWordId()
            newWord.text = Helper.stripWordLeaveWhiteSpace(text)
            newWord.cats = cats.toMutableList()
            newWord.snippetsList = snippetsList.toMutableList()
            newWord.eventList = eventList.toMutableList()
            newWord.connectId = connectId
            newWord.imgUrl = imgUrl
            newWord.spheres = spheres.toMutableList()
            if(Main.getWordByText(newWord.text) != null) newWord = null
            return newWord
        }
    }

}