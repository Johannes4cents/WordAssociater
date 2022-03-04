package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.*
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.Drama
import com.google.firebase.firestore.Exclude

data class Snippet(override var content: String = "",
                   override var id: Long = 0,
                   override var wordList: MutableList<Long> = mutableListOf(0),
                   var header: String = "",
                   var connectedSnippetsList: MutableList<Long> = mutableListOf(),
                   var layer: Int = 1,
                   override var characterList: MutableList<Long> = mutableListOf(22),
                   override var nuwList: MutableList<Long> = mutableListOf(),
                   override var storyLineList: MutableList<Long> = mutableListOf(),
                   override var locationList: MutableList<Long> = mutableListOf(),
                   override var itemList: MutableList<Long> = mutableListOf(),
                   override var date: Date = Date(0,"May",1000),
                   override var eventList: MutableList<Long> = mutableListOf(),
                   var drama: Drama = Drama.None,
                   override var type: Type = Type.Snippet
): StoryPart(id, content, wordList, characterList, nuwList, storyLineList, itemList, locationList, eventList, date, type) {

    @Exclude
    override var sortingOrder: Int = id.toInt()

    @Exclude
    var recyclerHeader = false

    @Exclude
    var onSnippetClicked : ((snippet:Snippet) -> Unit) ? = null

    @Exclude
    var pinned = false

    @Exclude
    fun getNuws(): List<Nuw> {
        val nuws = mutableListOf<Nuw>()
        val notFoundList = mutableListOf<Long>()
        for(id in nuwList) {
            val nuw = Main.getNuwById(id)
            if(nuw != null) nuws.add(nuw)
            else notFoundList.add(id)
        }

        for(id in notFoundList) {
            nuwList.remove(id)
            FireSnippets.update(this.id, "nuwList", nuwList)
        }
        return nuws
    }

    @Exclude
    fun updateContent(content: String) {
        if(this.content != content) {
            this.content = content
            FireSnippets.update(id, "content", content)
        }
    }

    @Exclude
    fun getConnectedSnippets(): List<Snippet> {
        val snipList = mutableListOf<Snippet>()
        val notFound = mutableListOf<Long>()
        for(id in connectedSnippetsList) {
            val snip = Main.getSnippet(id)
            if(snip != null) snipList.add(snip)
            else notFound.add(id)
        }

        for(id in notFound) {
            connectedSnippetsList.remove(id)
            FireSnippets.update(id, "connectedSnippetsList", connectedSnippetsList)
        }
        return snipList
    }

    @Exclude
    fun delete() {
        for(w in getWords()) {
            w.used -= 1
            w.snippetsList.remove(id)
            FireWords.update(w.id, "used", w.used)
            FireWords.update(w.id, "snippetsList", w.snippetsList)
        }

        for(c in getCharacters()) {
            c.snippetsList.remove(id)
            FireChars.update(c.id, "snippetsList", c.snippetsList)
        }

        for(snippet in getConnectedSnippets()) {
            snippet.connectedSnippetsList.remove(id)
            FireSnippets.update(snippet.id, "connectedSnippets", snippet.connectedSnippetsList)
        }

        for(storyLine in getStoryLines()) {
            storyLine.snippetsList.remove(id)

        }

        for(l in getLocations()) {
            l.snippetsList.remove(id)
            FireLocations.update(l.id, "snippetsList", l.snippetsList)
        }

        for(e in getEvents()) {
            e.snippetsList.remove(id)
            FireEvents.update(e.id, "snippetsList", e.snippetsList)
        }

        for(i in getItems()) {
            i.snippetsList.remove(id)
            FireItems.update(i.id, "snippetsList", i.snippetsList)
        }

        FireSnippets.delete(id)
    }

    @Exclude
    fun copyMe(): Snippet {
        val newSnippet = Snippet()
        newSnippet.connectedSnippetsList = connectedSnippetsList.toMutableList()
        newSnippet.drama = drama
        newSnippet.characterList = characterList.toMutableList()
        newSnippet.recyclerHeader = recyclerHeader
        newSnippet.storyLineList = storyLineList
        newSnippet.eventList = eventList
        newSnippet.itemList = itemList
        newSnippet.locationList = locationList
        newSnippet.id = 999999999999
        newSnippet.content = content
        newSnippet.nuwList = nuwList.toMutableList()
        newSnippet.wordList = wordList.toMutableList()

        oldStoryPart = newSnippet
        return newSnippet
    }

}