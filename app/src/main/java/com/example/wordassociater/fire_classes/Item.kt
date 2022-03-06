package com.example.wordassociater.fire_classes

import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireEvents
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.utils.LiveClass
import com.google.firebase.firestore.Exclude

data class Item(
        override var id: Long = 0,
        override var name: String = "",
        override var connectId: Long = 0,
        override var imgUrl: String = "",
        override var image: Long = 49
        ): SnippetPart, LiveClass {
    @Exclude
    override var selected = false

    override var importance: SnippetPart.Importance = SnippetPart.Importance.Main

    @Exclude
    override var isAHeader: Boolean = false

    override var eventList: MutableList<Long> = mutableListOf()
    override var description: String = ""
    override var storyLineList: MutableList<Long> = mutableListOf()
    override var snippetsList: MutableList<Long> = mutableListOf()
    override var wordList: MutableList<Long> = mutableListOf()

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
    override val liveStoryLinesOnly = MutableLiveData<List<StoryLine>>()

    @Exclude
    override var oldSnippetPart : SnippetPart? = null


    override fun delete() {
        for(id in snippetsList) {
            val snippet = Main.getSnippet(id)
            if(snippet != null) {
                snippet.itemList.remove(this.id)
                FireSnippets.update(snippet.id, "itemList", snippet.itemList)
            }
        }

        for(id in eventList) {
            val event = Main.getEvent(id)
            if(event != null) {
                event.itemList.remove(this.id)
                FireEvents.update(event.id, "itemList", event.itemList)
            }
        }

        val word = Main.wordsList.value!!.find { w -> w.connectId == connectId }
        word?.delete()
    }

    @Exclude
    override fun copyMe(): Item {
        val newItem = Item()
        newItem.storyLineList = storyLineList.toMutableList()
        newItem.eventList = eventList.toMutableList()
        newItem.id = 999999999999
        newItem.description = description
        newItem.wordList = wordList.toMutableList()
        newItem.snippetsList = snippetsList.toMutableList()
        newItem.connectId = connectId
        oldSnippetPart = newItem
        return newItem
    }
}