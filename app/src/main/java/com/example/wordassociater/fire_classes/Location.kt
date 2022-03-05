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
    override var storyLineList: MutableList<Long> = mutableListOf()
    override var snippetsList: MutableList<Long> = mutableListOf()
    override var eventList: MutableList<Long> = mutableListOf()
    override var wordList: MutableList<Long> = mutableListOf()

    @Exclude
    override var selected = false
    @Exclude
    override var isAHeader: Boolean = false

    @Exclude
    override var sortingOrder: Int = id.toInt()

    @Exclude
    override val liveStoryLines = MutableLiveData<List<StoryLine>>()
    @Exclude
    override val liveSnippets = MutableLiveData<List<Snippet>>()
    @Exclude
    override val liveWords = MutableLiveData<List<Word>>()
    @Exclude
    override val liveEvents = MutableLiveData<List<Event>>()
    @Exclude
    override lateinit var oldSnippetPart : SnippetPart

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
        oldSnippetPart = newLocation
        return newLocation
    }
}