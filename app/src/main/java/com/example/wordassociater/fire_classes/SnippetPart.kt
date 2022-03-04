package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWords
import com.google.firebase.firestore.Exclude

interface SnippetPart {
    var id: Long
    var name: String
    var connectId: Long
    var imgUrl : String
    var snippetsList: MutableList<Long>
    var eventList: MutableList<Long>
    var wordsList: MutableList<Long>

    enum class Importance(val text: String) {Main("Main"), Side("Side"), Minor("Minor"), Mentioned("Mentioned")}
    @Exclude
    fun delete()

    @Exclude
    fun getEventsAsSnippetPart(): List<Event> {
        val list = mutableListOf<Event>()
        val toRemoveList = mutableListOf<Long>()
        for(id in eventList) {
            val event = Main.getEvent(id)
            if(event != null) list.add(event)
            else toRemoveList.add(id)
        }

        for(id in toRemoveList) {
            eventList.remove(id)
            FireChars.update(this.id, "eventList", eventList)
        }
        return list
    }

    @Exclude
    fun getSnippets(): List<Snippet> {
        val list = mutableListOf<Snippet>()
        val toRemoveList = mutableListOf<Long>()
        for(id in eventList) {
            val snippet = Main.getSnippet(id)
            if(snippet != null) list.add(snippet)
            else toRemoveList.add(id)
        }

        for(id in toRemoveList) {
            snippetsList.remove(id)
            FireChars.update(this.id, "snippetsList", eventList)
        }
        return list
    }


    @Exclude
    fun createWord() {
        val connectId = FireStats.getConnectId()
        val word = Word(
                id = FireStats.getWordId(),
                text = name,
                imgUrl = this.imgUrl,
                connectId = connectId
        )
        this.connectId = connectId
        when(this) {
            is Character -> word.cats.add(1)
            is Location -> word.cats.add(2)
            is Event -> word.cats.add(3)
            is Item -> word.cats.add(4)
        }
        FireWords.add(word)
    }


}