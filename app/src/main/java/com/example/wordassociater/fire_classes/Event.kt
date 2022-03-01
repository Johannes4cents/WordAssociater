package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.firestore.*
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

class Event(
        override var id: Long = 0,
        override var content: String = "",
        var description: String = "",
        var snippetsList: MutableList<Long> = mutableListOf(),
        override var characterList: MutableList<Long> = mutableListOf(22),
        override var wordList: MutableList<Long> = mutableListOf(0),
        override var nuwList: MutableList<Long> = mutableListOf(),
        override var storyLineList: MutableList<Long> = mutableListOf(),
        override var date: Date = Date(0,"May",1100),
        override var type: Type = Type.Event,
        var image: Image = Image.Airplane
): StoryPart(id, content, characterList, wordList, nuwList, storyLineList, date, type) {
    enum class Image { Airplane, Crown, Explosion, Food, Handshake, Party, Pistol, Shield , Spy}

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

