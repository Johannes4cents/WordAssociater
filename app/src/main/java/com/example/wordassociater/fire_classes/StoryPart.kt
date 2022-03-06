package com.example.wordassociater.fire_classes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireEvents
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireStoryLines
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.LiveClass
import com.google.firebase.firestore.Exclude

open class StoryPart(
        override var id : Long,
        open var content: String,
        open var wordList: MutableList<Long>,
        open var characterList: MutableList<Long>,
        open var nuwList: MutableList<Long>,
        open var storyLineList: MutableList<Long>,
        open var itemList: MutableList<Long>,
        open var locationList: MutableList<Long>,
        open var eventList: MutableList<Long>,
        open var date: Date,
        open var type: Type,

        ): LiveClass {
    enum class Type { Snippet, Event, Header, Prose }

    @Exclude override var selected: Boolean = false
    @Exclude override var isAHeader: Boolean = false


    override var image: Long = 0
    override var sortingOrder: Int = id.toInt()
    override var name: String = ""

    @get:Exclude
    var oldStoryPart: StoryPart? = null

    @get:Exclude
    var isStoryPartHeader = false

    @get:Exclude
    val liveWords = MutableLiveData<List<LiveClass>>()

    @get:Exclude
    val liveWordsSearch = MutableLiveData<List<Word>>()

    @get:Exclude
    val liveCharacter = MutableLiveData<List<LiveClass>>()

    @get:Exclude
    val liveSelectedStoryLines = MutableLiveData<List<LiveClass>>()

    @get:Exclude
    val liveItems = MutableLiveData<List<LiveClass>>()

    @get:Exclude
    val liveLocations = MutableLiveData<List<LiveClass>>()

    @get:Exclude
    val liveEvents = MutableLiveData<List<LiveClass>>()

    @get:Exclude
    var index = 0


    @Exclude
    fun getItems(): MutableList<Item> {
        val items = mutableListOf<Item>()
        for(id in itemList) {
            val item = Main.getItem(id)
            if(item != null) items.add(item)
        }
        return items
    }

    @Exclude
    fun getFullItemList(): List<Item> {
        val allItems = Main.itemList.value!!.toMutableList()
        for(c in allItems) {
            c.selected = getItems().contains(c)
        }
        liveItems.value = allItems
        return allItems
    }

    @Exclude
    fun takeItem(character: Item) {
        if(getItems().contains(character)) itemList.remove(character.id)
        else itemList.add(character.id)
        getFullItemList()
    }

    @Exclude
    fun updateItems() {
        if(oldStoryPart!!.itemList != itemList) {
            // update newly added items snippetLists
            for(id in itemList) {
                if(!oldStoryPart!!.itemList.contains(id)) {
                    val item = Main.getItem(id)
                    when(this) {
                        is Snippet -> {
                            item!!.snippetsList.add(this.id)
                            FireChars.update(item.id, "snippetsList", item.snippetsList)

                        }
                        is Event -> {
                            item!!.eventList.add(this.id)
                            FireEvents.update(item.id, "eventList", item.eventList)
                        }
                    }
                }
            }
            // update removed items snippetLists
            for(id in oldStoryPart!!.itemList) {
                if(!itemList.contains(id)) {
                    val item = Main.getItem(id)
                    when(this) {
                        is Snippet -> {
                            item!!.snippetsList.remove(this.id)
                            FireChars.update(item.id, "snippetsList", item.snippetsList)

                        }
                        is Event -> {
                            item!!.eventList.remove(this.id)
                            FireEvents.update(item.id, "eventList", item.eventList)
                        }
                    }
                }
            }

            // update the Snippets ItemList
            when(this) {
                is Snippet -> FireSnippets.update(this.id, "itemList", itemList)
                is Event -> FireEvents.update(this.id, "itemList", itemList)
            }

        }
    }

    @Exclude
    fun getLocations(): MutableList<Location> {
        val locations = mutableListOf<Location>()
        for(id in locationList) {
            val location = Main.getLocation(id)
            if(location != null) locations.add(location)
        }
        return locations
    }

    @Exclude
    fun getFullLocationList(): List<Location> {
        val allLocations = Main.locationList.value!!.toMutableList()
        for(l in allLocations) {
            l.selected = getLocations().contains(l)
        }
        liveLocations.value = allLocations
        return allLocations
    }

    @Exclude
    fun takeLocation(character: Location) {
        if(getLocations().contains(character)) locationList.remove(character.id)
        else locationList.add(character.id)
        getFullLocationList()
    }

    @Exclude
    fun updateLocation() {
        Log.i("updateTest", "oldStoryPart.locationList != locationList = ${oldStoryPart!!.locationList != locationList}")
        if(oldStoryPart!!.locationList != locationList) {
            // update newly added locations snippetLists
            for(id in locationList) {
                if(!oldStoryPart!!.locationList.contains(id)) {
                    val location = Main.getLocation(id)
                    when(this) {
                        is Snippet -> {
                            location!!.snippetsList.add(this.id)
                            FireChars.update(location.id, "snippetsList", location.snippetsList)

                        }
                        is Event -> {
                            location!!.eventList.add(this.id)
                            FireEvents.update(location.id, "eventList", location.eventList)
                        }
                    }
                }
            }
            // update removed locations snippetLists
            for(id in oldStoryPart!!.locationList) {
                if(!locationList.contains(id)) {
                    val location = Main.getLocation(id)
                    when(this) {
                        is Snippet -> {
                            location!!.snippetsList.remove(this.id)
                            FireChars.update(location.id, "snippetsList", location.snippetsList)

                        }
                        is Event -> {
                            location!!.eventList.remove(this.id)
                            FireEvents.update(location.id, "eventList", location.eventList)
                        }
                    }
                }
            }

            // update the Snippets LocationList
            when(this) {
                is Snippet -> FireSnippets.update(this.id, "locationList", locationList)
                is Event -> FireEvents.update(this.id, "locationList", locationList)
            }

        }
    }

    @Exclude
    open fun getWords(): MutableList<Word> {
        val words = mutableListOf<Word>()
        for(id in wordList) {
            val word = Main.getWord(id)
            if(word != null) words.add(word)
        }
        return words
    }

    @Exclude
    open fun getFullWordsList() : List<Word> {
        val allWords = Main.wordsList.value!!.toMutableList()
        for(w in allWords) {
            w.selected = getWords().contains(w)
        }
        liveWords.value = allWords
        return allWords
    }

    @Exclude
    open fun takeWord(word:Word) {
        if(getWords().contains(word)) {
            wordList.remove(word.id)
        }
        else wordList.add(word.id)
        getFullWordsList()
    }

    @Exclude
    open fun updateWords() {
        Log.i("updateTest", "oldStoryPart.wordList != wordList = ${oldStoryPart!!.wordList != wordList}")
        if(oldStoryPart!!.wordList != wordList) {
            // update newly added words snippetLists
            for(id in wordList) {
                if(!oldStoryPart!!.wordList.contains(id)) {
                    val word = Main.getWord(id)
                    when(this) {
                        is Snippet -> {
                            word!!.snippetsList.add(this.id)
                            FireChars.update(word.id, "snippetsList", word.snippetsList)
                            word.increaseWordUsed()

                        }
                        is Event -> {
                            word!!.eventList.add(this.id)
                            FireEvents.update(word.id, "eventList", word.eventList)
                            word.increaseWordUsed()
                        }
                    }
                }
            }
            // update removed words snippetLists
            for(id in oldStoryPart!!.wordList) {
                if(!wordList.contains(id)) {
                    val word = Main.getWord(id)
                    when(this) {
                        is Snippet -> {
                            word!!.snippetsList.remove(this.id)
                            WordConnection.disconnect(word, this.id)
                            FireChars.update(word.id, "snippetsList", word.snippetsList)
                            word.decreaseWordUsed()

                        }
                        is Event -> {
                            word!!.eventList.remove(this.id)
                            WordConnection.disconnect(word, this.id)
                            FireEvents.update(word.id, "eventList", word.eventList)
                            word.decreaseWordUsed()
                        }
                    }
                }
            }

            // update the Snippets WordList
            when(this) {
                is Snippet -> FireSnippets.update(this.id, "wordList", wordList)
                is Event -> FireEvents.update(this.id, "wordList", wordList)
            }

        }
    }

    @Exclude
    fun getCharacters(): List<Character> {
        val chars = mutableListOf<Character>()
        for(id in characterList) {
            val char = Main.characterList.value?.find { c -> c.id == id }
            if(char != null) chars.add(char)
        }

        return chars
    }

    @Exclude
    fun getFullCharacterList(): List<Character> {
        val allChars = Main.characterList.value!!.toMutableList()
        for(c in allChars) {
            c.selected = getCharacters().contains(c)
        }
        liveCharacter.value = allChars
        return allChars
    }

    @Exclude
    fun takeCharacter(character: Character) {
        if(getCharacters().contains(character)) characterList.remove(character.id)
        else characterList.add(character.id)
        getFullCharacterList()
    }

    @Exclude
    fun updateCharacter() {

        if(oldStoryPart!!.characterList != characterList) {
            // update newly added characters snippetLists
            for(id in characterList) {
                if(!oldStoryPart!!.characterList.contains(id)) {
                    val character = Main.getCharacter(id)
                    when(this) {
                        is Snippet -> {
                            character!!.snippetsList.add(this.id)
                            FireChars.update(character.id, "snippetsList", character.snippetsList)

                        }
                        is Event -> {
                            character!!.eventList.add(this.id)
                            FireEvents.update(character.id, "eventList", character.eventList)
                        }
                    }
                }
            }
            // update removed characters snippetLists
            for(id in oldStoryPart!!.characterList) {
                if(!characterList.contains(id)) {
                    val character = Main.getCharacter(id)
                    when(this) {
                        is Snippet -> {
                            character!!.snippetsList.remove(this.id)
                            FireChars.update(character.id, "snippetsList", character.snippetsList)

                        }
                        is Event -> {
                            character!!.eventList.remove(this.id)
                            FireEvents.update(character.id, "eventList", character.eventList)
                        }
                    }
                }
            }

            // update the Snippets CharacterList
            when(this) {
                is Snippet -> FireSnippets.update(this.id, "characterList", characterList)
                is Event -> FireSnippets.update(this.id, "characterList", characterList)
            }

        }
    }

    @Exclude
    open fun getStoryLines(): List<StoryLine> {
        val list = mutableListOf<StoryLine>()
        for(id in storyLineList) {
            val sl = Main.getStoryLine(id)
            if(sl != null) list.add(sl)
        }
        return list
    }

    @Exclude
    open fun getFullStoryLineList(): List<StoryLine> {
        val allStoryLines = Main.storyLineList.value!!.toMutableList()
        for(sl in allStoryLines) {
            sl.selected = getStoryLines().contains(sl)
        }
        liveSelectedStoryLines.value = allStoryLines.filter { sl -> sl.type != StoryLine.Type.SnippetPart }
        return allStoryLines
    }

    @Exclude
    open fun takeStoryLine(storyLine: StoryLine) {
        if(getStoryLines().contains(storyLine)) storyLineList.remove(storyLine.id)
        else storyLineList.add(storyLine.id)
        getFullStoryLineList()
    }

    @Exclude
    open fun updateStoryLines() {
        Log.i("updateTest", "oldStoryPart.storyLineList != storyLineList = ${oldStoryPart!!.storyLineList != storyLineList}")
        if(oldStoryPart!!.storyLineList != storyLineList) {
            // update newly added storyLines snippetLists
            for(id in storyLineList) {
                if(!oldStoryPart!!.storyLineList.contains(id)) {
                    val storyLine = Main.getStoryLine(id)
                    when(this) {
                        is Snippet -> {
                            storyLine!!.snippetsList.add(this.id)
                            FireStoryLines.update(storyLine.id, "snippetsList", storyLine.snippetsList)

                        }
                        is Event -> {
                            storyLine!!.eventList.add(this.id)
                            FireStoryLines.update(storyLine.id, "eventList", storyLine.eventList)
                        }
                    }
                }
            }
            // update removed storyLines snippetLists
            for(id in oldStoryPart!!.storyLineList) {
                if(!storyLineList.contains(id)) {
                    val storyLine = Main.getStoryLine(id)
                    when(this) {
                        is Snippet -> {
                            storyLine!!.snippetsList.remove(this.id)
                            FireStoryLines.update(storyLine.id, "snippetsList", storyLine.snippetsList)

                        }
                        is Event -> {
                            storyLine!!.eventList.remove(this.id)
                            FireStoryLines.update(storyLine.id, "eventList", storyLine.eventList)
                        }
                    }
                }
            }
            // update the Snippets StoryLineList
            when(this) {
                is Snippet -> FireSnippets.update(this.id, "storyLineList", storyLineList)
                is Event -> FireEvents.update(this.id, "storyLineList", storyLineList)
            }
        }
    }

    @Exclude
    fun getEvents(): List<Event> {
        val events = mutableListOf<Event>()
        for(id in eventList) {
            val event = Main.getEvent(id)
            if(event != null) {
                events.add(event)
            }
        }
        return events
    }

    @Exclude
    open fun getFullEventList(): List<Event> {
        val allEvents = Main.eventList.value!!.toMutableList()
        for(sl in allEvents) {
            sl.selected = getEvents().contains(sl)
        }
        liveEvents.value = allEvents
        return allEvents
    }

    @Exclude
    open fun takeEvent(event: Event) {
        if(getEvents().contains(event)) eventList.remove(event.id)
        else eventList.add(event.id)
        getFullEventList()
    }

    @Exclude
    open fun updateEvents() {
        if(oldStoryPart!!.eventList != eventList) {
            // update newly added events snippetLists
            for(id in eventList) {
                if(!oldStoryPart!!.eventList.contains(id)) {
                    val event = Main.getEvent(id)
                    when(this) {
                        is Snippet -> {
                            event!!.snippetsList.add(this.id)
                            FireEvents.update(event.id, "snippetsList", event.snippetsList)

                        }
                        is Event -> {
                            event!!.eventList.add(this.id)
                            FireEvents.update(event.id, "eventList", event.eventList)
                        }
                    }
                }
            }
            // update removed events snippetLists
            for(id in oldStoryPart!!.eventList) {
                if(!eventList.contains(id)) {
                    val event = Main.getEvent(id)
                    when(this) {
                        is Snippet -> {
                            event!!.snippetsList.remove(this.id)
                            FireEvents.update(event.id, "snippetsList", event.snippetsList)

                        }
                        is Event -> {
                            event!!.eventList.remove(this.id)
                            FireEvents.update(event.id, "eventList", event.eventList)
                        }
                    }
                }
            }

            // update the Snippets EventList
            when(this) {
                is Snippet -> FireSnippets.update(this.id, "eventList", eventList)
                is Event -> FireEvents.update(this.id, "eventList", eventList)
            }
        }
    }
}