package com.example.wordassociater.fire_classes

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
    override var imgUrl: String = ""
    override var image: Long = 10
    override var snippetsList: MutableList<Long> = mutableListOf()
    override var eventList: MutableList<Long> = mutableListOf()
    override var wordsList: MutableList<Long> = mutableListOf()

    var wordList: MutableList<Long> = mutableListOf()

    @Exclude
    override var selected = false
    @Exclude
    override var isAHeader: Boolean = false

    @Exclude
    override var sortingOrder: Int = id.toInt()



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
}