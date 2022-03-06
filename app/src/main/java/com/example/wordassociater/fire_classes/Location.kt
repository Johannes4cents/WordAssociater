package com.example.wordassociater.fire_classes

import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireEvents
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.utils.LiveClass
import com.google.firebase.firestore.Exclude

data class Location(
        override var id: Long = 0,
        override var name: String = "",
        override var connectId: Long = 0,
): SnippetPart, LiveClass {
    override var description: String = ""
    override var imgUrl: String = ""
    override var image: Long = 10
    override var importance: SnippetPart.Importance = SnippetPart.Importance.Main
    override var storyLineList: MutableList<Long> = mutableListOf()
    override var snippetsList: MutableList<Long> = mutableListOf()
    override var eventList: MutableList<Long> = mutableListOf()
    override var wordList: MutableList<Long> = mutableListOf()


    @get:Exclude
    override var selected = false

    @get:Exclude
    override var isAHeader: Boolean = false

    @get:Exclude
    override var sortingOrder: Int = id.toInt()

    @get:Exclude
    override val liveStoryLines = MutableLiveData<List<LiveClass>>()

    @get:Exclude
    override val liveSnippets = MutableLiveData<List<LiveClass>>()

    @get:Exclude
    override val liveWords = MutableLiveData<List<LiveClass>>()
    @get:Exclude
    override val liveEvents = MutableLiveData<List<LiveClass>>()

    @get:Exclude
    override val liveWordsSearch = MutableLiveData<List<Word>>()

    @get:Exclude
    override val liveMyStoryLines = MutableLiveData<List<StoryLine>>()

    @get:Exclude
    override var oldSnippetPart : SnippetPart? = null

    @Exclude
    override fun delete() {
        for(id in snippetsList) {
            val snippet = Main.getSnippet(id)
            if(snippet != null) {
                snippet.locationList.remove(this.id)
                FireSnippets.update(snippet.id, "locationList", snippet.locationList)
            }
        }

        for(id in eventList) {
            val event = Main.getEvent(id)
            if(event != null) {
                event.locationList.remove(this.id)
                FireEvents.update(event.id, "locationList", event.locationList)
            }
        }

        val word = Main.wordsList.value!!.find { w -> w.connectId == connectId }
        word?.delete()
    }

    @Exclude
    override fun copyMe(): Location {
        val newLocation = Location()
        newLocation.storyLineList = storyLineList.toMutableList()
        newLocation.eventList = eventList.toMutableList()
        newLocation.id = 999999999999
        newLocation.description = description
        newLocation.wordList = wordList.toMutableList()
        newLocation.snippetsList = snippetsList.toMutableList()
        newLocation.connectId = connectId
        return newLocation
    }
}