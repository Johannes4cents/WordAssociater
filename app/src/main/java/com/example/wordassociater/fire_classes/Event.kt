package com.example.wordassociater.fire_classes

import android.util.Log
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.firestore.*
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

class Event(
        override var id: Long = 0,
        override var content: String = "",
        override var characterList: MutableList<Long> = mutableListOf(22),
        override var wordList: MutableList<Long> = mutableListOf(0),
        override var nuwList: MutableList<Long> = mutableListOf(),
        override var storyLineList: MutableList<Long> = mutableListOf(),
        override var date: Date = Date(0,"May",1100),
        override var type: Type = Type.Event,
        var connectId: Long = 0
): StoryPart(id, content, characterList, wordList, nuwList, storyLineList, date, type) {
    var description: String = ""
    var snippetsList: MutableList<Long> = mutableListOf()
    var image: Image = Image.Airplane

    enum class Image { Airplane, Crown, Explosion, Food, Handshake, Party, Pistol, Shield , Spy}
    @Exclude
    fun createWord() {
        val connectId = FireStats.getConnectId()
        val word = Word(
                id = FireStats.getWordId(),
                text = content,
                connectId = connectId
        )
        word.cats.add(8)
        this.connectId = connectId
        wordList.add(word.id)
        FireWords.add(word)
    }

    @Exclude
    fun handleWordConnections() {
        WordConnection.connect(this)
    }

    @Exclude
    fun getStoryLines(): List<StoryLine> {
        val list = mutableListOf<StoryLine>()
        val toRemoveList = mutableListOf<Long>()
        for(id in storyLineList) {
            val sl = Main.getStoryLine(id)
            if(sl != null) list.add(sl)
            else toRemoveList.add(id)
        }

        for(id in toRemoveList) {
            storyLineList.remove(id)
            FireEvents.update(this.id, "storyLineList", storyLineList)
        }

        return list
    }

    @Exclude
    fun getWords(): MutableList<Word> {
        val words = mutableListOf<Word>()
        val notFound = mutableListOf<Long>()
        for(id in wordList) {
            val found = Main.getWord(id)
            if(found != null) words.add(found)
            else notFound.add(id)
        }

        for(id in notFound) {
            wordList.remove(id)
            FireSnippets.update(id, "wordList", wordList)
        }
        return words
    }

    @Exclude
    fun getImage(): Int {
        return when(image) {
            Image.Airplane -> R.drawable.event_airplane
            Image.Crown -> R.drawable.event_crown
            Image.Explosion -> R.drawable.event_explosion
            Image.Food -> R.drawable.event_food
            Image.Handshake -> R.drawable.event_handshake
            Image.Party -> R.drawable.event_party
            Image.Pistol -> R.drawable.event_pistol
            Image.Shield -> R.drawable.event_shield
            Image.Spy -> R.drawable.event_spy
        }
    }

    @Exclude
    fun delete() {
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

