package com.example.wordassociater.fire_classes

import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.*
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.LiveClass
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
): StoryPart(id,name, wordList, characterList, nuwList, storyLineList, itemList, locationList, eventList, date, type), SnippetPart {

     override var image: Long = 1L
     override var importance: SnippetPart.Importance = SnippetPart.Importance.Main
     override var description: String = ""
     override var imgUrl: String = ""
     override var snippetsList: MutableList<Long> = mutableListOf()


     @get:Exclude
     override val liveSnippets = MutableLiveData<List<LiveClass>>()

     @get:Exclude
     override val liveStoryLines = MutableLiveData<List<LiveClass>>()

     @get:Exclude
     override val liveMyStoryLines = MutableLiveData<List<StoryLine>>()

     @get:Exclude
     override var oldSnippetPart : SnippetPart? = null


     @Exclude
     override var sortingOrder: Int = id.toInt()

     @Exclude
     override fun getWords(): MutableList<Word> {
         val words = mutableListOf<Word>()
         for(id in wordList) {
             val word = Main.getWord(id)
             if(word != null) words.add(word)
         }
         return words
     }

     @Exclude
     override fun getFullWordsList(): List<Word> {
         val allWords = Main.wordsList.value!!.toMutableList()
         for(w in allWords) {
             w.selected = getWords().contains(w)
         }
         liveWords.value = allWords
         liveWordsSearch.value = allWords
         return allWords
     }

     @Exclude
     override fun takeWord(word: Word) {
         if(getWords().contains(word)) {
             wordList.remove(word.id)
         }
         else wordList.add(word.id)
         getFullWordsList()
     }

     @Exclude
     override fun updateWords() {
         if(oldStoryPart!!.wordList != wordList) {
             // update newly added words snippetLists
             for(id in wordList) {
                 if(!oldStoryPart!!.wordList.contains(id)) {
                     val word = Main.getWord(id)
                     word!!.eventList.add(this.id)
                     FireEvents.update(word.id, "eventList", word.eventList)
                     word.increaseWordUsed()
                 }
             }
             // update removed words snippetLists
             for(id in oldStoryPart!!.wordList) {
                 if(!wordList.contains(id)) {
                     val word = Main.getWord(id)
                     word!!.eventList.remove(this.id)
                     WordConnection.disconnect(word, this.id)
                     FireEvents.update(word.id, "eventList", word.eventList)
                     word.decreaseWordUsed()
                 }
             }
             // update the Snippets WordList
             FireEvents.update(this.id, "wordList", wordList)
         }
     }

     @Exclude
     override fun getStoryLines(): List<StoryLine> {
         val list = mutableListOf<StoryLine>()
         for(id in storyLineList) {
             val sl = Main.getStoryLine(id)
             if(sl != null) list.add(sl)
         }
         return list
     }

    @Exclude
     override fun getMyStoryLineList(): List<StoryLine> {
         val storyLines = Main.storyLineList.value!!.toMutableList()
         for(sl in storyLines) {
             sl.selected = storyLineList.contains(sl.id)
         }
         liveStoryLines.value = storyLines
         return storyLines
     }

     @Exclude
     fun getSelectedStoryLineList(): List<StoryLine> {
         val allStoryLines = Main.storyLineList.value!!.toMutableList()
         for(sl in allStoryLines) {
             sl.selected = getStoryLines().contains(sl)
         }
         liveSelectedStoryLines.value = allStoryLines
         return allStoryLines
     }

     @Exclude
     override fun takeStoryLine(storyLine: StoryLine) {
         if(getStoryLines().contains(storyLine)) storyLineList.remove(storyLine.id)
         else storyLineList.add(storyLine.id)
         getSelectedStoryLineList()
     }

     @Exclude
     override fun updateStoryLines() {
         if(oldStoryPart!!.storyLineList != storyLineList) {
             // update newly added storyLines snippetLists
             for(id in storyLineList) {
                 if(!oldStoryPart!!.storyLineList.contains(id)) {
                     val storyLine = Main.getStoryLine(id)
                     storyLine!!.eventList.add(this.id)
                     FireStoryLines.update(storyLine.id, "eventList", storyLine.eventList)
                 }
             }
             // update removed storyLines snippetLists
             for(id in oldStoryPart!!.storyLineList) {
                 if(!storyLineList.contains(id)) {
                     val storyLine = Main.getStoryLine(id)
                     storyLine!!.eventList.remove(this.id)
                     FireStoryLines.update(storyLine.id, "eventList", storyLine.eventList)
                 }
             }
             // update the Snippets StoryLineList
             FireEvents.update(this.id, "storyLineList", storyLineList)
         }
     }

     @Exclude
     override fun getFullEventList(): List<Event> {
         val allEvents = Main.eventList.value!!.toMutableList()
         for(sl in allEvents) {
             sl.selected = getEvents().contains(sl)
         }
         liveEvents.value = allEvents
         return allEvents
     }

     @Exclude
     override fun takeEvent(event: Event) {
         if(getEvents().contains(event)) eventList.remove(event.id)
         else eventList.add(event.id)
         getFullEventList()
     }

     @Exclude
     override fun updateEvents() {
         if(oldStoryPart!!.eventList != eventList) {
             // update newly added events snippetLists
             for(id in eventList) {
                 if(!oldStoryPart!!.eventList.contains(id)) {
                     val event = Main.getEvent(id)
                     event!!.eventList.add(this.id)
                     FireEvents.update(event.id, "eventList", event.eventList)
                 }
             }
             // update removed events snippetLists
             for(id in oldStoryPart!!.eventList) {
                 if(!eventList.contains(id)) {
                     val event = Main.getEvent(id)
                     event!!.eventList.remove(this.id)
                     FireEvents.update(event.id, "eventList", event.eventList)
                 }
             }

             // update the Snippets EventList
             FireEvents.update(this.id, "eventList", eventList)
         }
     }



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

        for(id in this.wordList) {
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


     @Exclude
     override fun copyMe(): Event {
         val newEvent = Event()
         newEvent.characterList = characterList.toMutableList()
         newEvent.storyLineList = storyLineList.toMutableList()
         newEvent.eventList = eventList.toMutableList()
         newEvent.itemList = itemList.toMutableList()
         newEvent.locationList = locationList.toMutableList()
         newEvent.id = 999999999999
         newEvent.content = content
         newEvent.description = description
         newEvent.nuwList = nuwList.toMutableList()
         newEvent.wordList = wordList.toMutableList()

         oldStoryPart = newEvent
         oldSnippetPart = newEvent
         return newEvent
     }




 }

