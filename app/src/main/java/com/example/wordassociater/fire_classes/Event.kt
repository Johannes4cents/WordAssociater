package com.example.wordassociater.fire_classes

import android.util.Log
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.*
import com.example.wordassociater.utils.Date
import com.google.firebase.firestore.Exclude

 class Event(
        override var id: Long = 0,
        override var name: String = "",
        override var characterList: MutableList<Long> = mutableListOf(22),
        override var wordList: MutableList<Long> = mutableListOf(0),
        override var nuwList: MutableList<Long> = mutableListOf(),
        override var storyLineList: MutableList<Long> = mutableListOf(),
        override var itemList: MutableList<Long> = mutableListOf(),
        override var locationList: MutableList<Long> = mutableListOf(),
        override var eventList: MutableList<Long> = mutableListOf(),
        override var date: Date = Date(0,"May",1100),
        override var type: Type = Type.Event,
        override var connectId: Long = 0
): StoryPart(id, name, wordList, characterList, nuwList, storyLineList, itemList, locationList, eventList, date, type), SnippetPart {

    var description: String = ""
    override var imgUrl: String = ""
    override var snippetsList: MutableList<Long> = mutableListOf()
     override var wordsList: MutableList<Long> = mutableListOf()

     @Exclude
     override var sortingOrder: Int = id.toInt()

    override var image: Long = 1L
    enum class Image { Airplane, Crown, Explosion, Food, Handshake, Party, Pistol, Shield , Spy}

    @Exclude
    fun handleWordConnections() {
        WordConnection.connect(this)
    }

    @Exclude
    override fun delete() {
        for(sl in storyLineList) {
            val sl = Main.getStoryLine(sl)
            if(sl != null) {
                sl.eventList.remove(id)
                Log.i("eventProb", "sl is ${sl.id} and its Found")
                FireStoryLines.update(sl.id, "eventList", sl.eventList)
            }
        }

        for(id in characterList) {
            val char = Main.getCharacter(id)
            if(char != null) {
                char.eventList.remove(id)
                FireChars.update(char.id, "eventList", char.eventList)
            }
        }

        for(id in wordList) {
            val word = Main.getWord(id)
            if(word != null) {
                word.eventList.remove(id)
                FireWords.update(word.id, "eventList", word.eventList)
            }
        }

        for(id in snippetsList) {
            val snippet = Main.getSnippet(id)
            if(snippet != null) {
                snippet.eventList.remove(id)
                FireSnippets.update(snippet.id, "eventList", snippet.eventList)
            }
        }

        FireEvents.delete(id)
    }



 }

