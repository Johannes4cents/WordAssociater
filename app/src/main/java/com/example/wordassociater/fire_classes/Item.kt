package com.example.wordassociater.fire_classes

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

    @Exclude
    override var isAHeader: Boolean = false

    override var eventList: MutableList<Long> = mutableListOf()
    override var snippetsList: MutableList<Long> = mutableListOf()
    override var wordsList: MutableList<Long> = mutableListOf()

    override var sortingOrder: Int = id.toInt()





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
}