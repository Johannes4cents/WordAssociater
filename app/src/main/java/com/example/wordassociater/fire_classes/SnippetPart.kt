package com.example.wordassociater.fire_classes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.*
import com.example.wordassociater.utils.Image
import com.example.wordassociater.utils.LiveClass
import com.google.firebase.firestore.Exclude

interface SnippetPart {
    var id: Long
    var name: String
    var description: String
    var connectId: Long
    var imgUrl : String
    var storyLineList: MutableList<Long>
    var snippetsList: MutableList<Long>
    var eventList: MutableList<Long>
    var wordList: MutableList<Long>
    var image: Long
    var importance: SnippetPart.Importance

    enum class Importance(val text: String) {Main("Main"), Side("Side"), Minor("Minor"), Mentioned("Mentioned")}
    enum class Type {Location, Event, Item, Character}
    @Exclude
    fun delete()

    val liveWordsSearch: MutableLiveData<List<Word>>
    val liveStoryLinesOnly: MutableLiveData<List<StoryLine>>
    val liveStoryLines: MutableLiveData<List<LiveClass>>
    val liveSnippets: MutableLiveData<List<LiveClass>>
    val liveWords: MutableLiveData<List<LiveClass>>
    val liveEvents: MutableLiveData<List<LiveClass>>
    val oldSnippetPart: SnippetPart?

    @Exclude
    fun copyMe(): SnippetPart

    @Exclude
    fun getImage(): Image {
        return Image.getDrawable(image)
    }

    @Exclude
    fun getWords(): MutableList<Word> {
        val words = mutableListOf<Word>()
        for(id in wordList) {
            val word = Main.getWord(id)
            if(word != null) words.add(word)
        }
        return words
    }

    @Exclude
    fun getFullWordsList() : List<Word> {
        Log.i("lagProb", "getFullWordsList called")
        val allWords = Main.wordsList.value!!.toMutableList()
        for(w in allWords) {
            w.selected = getWords().contains(w)
        }
        liveWords.value = allWords
        liveWordsSearch.value = allWords
        return allWords
    }

    @Exclude
    fun takeWord(word:Word) {
        if(getWords().contains(word)) {
            wordList.remove(word.id)
        }
        else wordList.add(word.id)
        getFullWordsList()
    }

    @Exclude
    fun updateWords() {
        Log.i("updateTest", "oldStoryPart.wordList != wordList = ${oldSnippetPart!!.wordList != wordList}")
        if(oldSnippetPart?.wordList != wordList) {
            // update newly added words snippetLists
            for(id in wordList) {
                if(!oldSnippetPart?.wordList?.contains(id)!!) {
                    val word = Main.getWord(id)
                    when(this) {
                        is Event -> {
                            word!!.eventList.add(this.id)
                            FireEvents.update(word.id, "eventList", word.eventList)
                            word.increaseWordUsed()
                        }
                    }
                }
            }
            // update removed words snippetLists
            for(id in oldSnippetPart?.wordList!!) {
                if(!wordList.contains(id)) {
                    val word = Main.getWord(id)
                    when(this) {
                        is Event -> {
                            word!!.eventList.remove(this.id)
                            WordConnection.disconnect(word, this.id)
                            FireEvents.update(word.id, "eventList", word.eventList)
                            word.decreaseWordUsed()
                        }
                    }
                }
            }

            when(this) {
                is Character -> FireChars.update(this.id, "wordList", wordList)
                is Event -> FireEvents.update(this.id, "wordList", wordList)
                is Item -> FireItems.update(this.id, "wordList", wordList)
                is Location -> FireLocations.update(this.id, "wordList", wordList)
            }

        }
    }

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
    fun getFullEventList(): List<Event> {
        val allEvents = Main.eventList.value!!.toMutableList()
        for(sl in allEvents) {
            sl.selected = getEventsAsSnippetPart().contains(sl)
        }
        liveEvents.value = allEvents
        return allEvents
    }

    @Exclude
    fun takeEvent(event: Event) {
        if(getEventsAsSnippetPart().contains(event)) eventList.remove(event.id)
        else eventList.add(event.id)
        getFullEventList()
    }

    @Exclude
    fun updateEvents() {
        Log.i("updateTest", "oldStoryPart.eventList != eventList = ${oldSnippetPart!!.eventList != eventList}")
        if(oldSnippetPart!!.eventList != eventList) {
            // update newly added events snippetLists
            for(id in eventList) {
                if(!oldSnippetPart!!.eventList.contains(id)) {
                    val event = Main.getEvent(id)
                    when(this) {
                        is Character -> {
                            event!!.characterList.add(this.id)
                            FireEvents.update(event.id, "characterList", event.characterList)

                        }
                        is Event -> {
                            event!!.eventList.add(this.id)
                            FireEvents.update(event.id, "eventList", event.eventList)
                        }

                        is Item -> {
                            event!!.itemList.add(this.id)
                            FireEvents.update(event.id, "itemList", event.itemList)
                        }

                        is Location -> {
                            event!!.locationList.add(this.id)
                            FireEvents.update(event.id, "locationList", event.locationList)
                        }
                    }
                }
            }
            // update removed events snippetLists
            for(id in oldSnippetPart!!.eventList) {
                if(!eventList.contains(id)) {
                    val event = Main.getEvent(id)
                    when(this) {
                        is Character -> {
                            event!!.characterList.remove(this.id)
                            FireEvents.update(event.id, "characterList", event.characterList)

                        }
                        is Event -> {
                            event!!.eventList.remove(this.id)
                            FireEvents.update(event.id, "eventList", event.eventList)
                        }

                        is Item -> {
                            event!!.itemList.remove(this.id)
                            FireEvents.update(event.id, "itemList", event.itemList)
                        }

                        is Location -> {
                            event!!.locationList.remove(this.id)
                            FireEvents.update(event.id, "locationList", event.locationList)
                        }
                    }
                }
            }

            // update the Snippets EventList
            when(this) {
                is Character -> FireChars.update(id, "eventList", eventList)
                is Location -> FireChars.update(id, "eventList", eventList)
                is Event -> FireChars.update(id, "eventList", eventList)
                is Item -> FireItems.update(id, "eventList", eventList)

            }

        }
    }

    @Exclude
    fun getStoryLines(): List<StoryLine> {
        val list = mutableListOf<StoryLine>()
        for(id in storyLineList) {
            val sl = Main.getStoryLine(id)
            if(sl != null) list.add(sl)
        }
        liveStoryLinesOnly.value = list
        return list
    }

    @Exclude
    fun selectStoryLine(storyLine: StoryLine) {
        val list = liveStoryLines.value!!.toMutableList()
        list.remove(storyLine)
        storyLine.selected = !storyLine.selected
        list.add(storyLine)
        liveStoryLines.value = list
    }

    @Exclude
    fun takeStoryLine(storyLine: StoryLine) {
        if(getStoryLines().contains(storyLine)) storyLineList.remove(storyLine.id)
        else storyLineList.add(storyLine.id)
        getFullStoryLineList()
    }

    @Exclude
    fun getFullStoryLineList(): List<StoryLine> {
        val storyLines = Main.storyLineList.value!!.toMutableList()
        val snippetsStoryLines = mutableListOf<StoryLine>()
        for(sl in storyLines) {
            sl.selected = storyLineList.contains(sl.id)
            if(sl.selected) snippetsStoryLines.add(sl)
        }
        liveStoryLines.value = storyLines
        liveStoryLinesOnly.value = snippetsStoryLines
        return storyLines
    }

    @Exclude
    fun updateStoryLines() {
        Log.i("updateTest", "oldStoryPart.storyLineList != storyLineList = ${oldSnippetPart!!.storyLineList != storyLineList}")
        when(this) {
            is Character -> FireChars.update(this.id, "storyLineList", storyLineList)
            is Event -> FireEvents.update(this.id, "storyLineList", storyLineList)
            is Location -> FireLocations.update(this.id, "storyLineList", storyLineList)
            is Item -> FireItems.update(this.id, "storyLineList", storyLineList)
        }
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

        // Fam
        val fam = Fam(id= FireStats.getFamNumber(), name)
        word.famList.add(fam.id)
        when(this) {
            is Character -> {
                FireChars.update(id, "connectId", connectId)
                word.cats.add(1)
                word.type = Word.Type.Character
            }
            is Location -> {
                FireLocations.update(id, "connectId", connectId)
                word.cats.add(2)
                word.type = Word.Type.Location
            }
            is Event -> {
                FireEvents.update(id, "connectId", connectId)
                word.cats.add(3)
                word.type = Word.Type.Event
            }
            is Item -> {
                FireItems.update(id, "connectId", connectId)
                word.cats.add(4)
                word.type = Word.Type.Item
            }
        }
        FireWords.add(word)

    }

}