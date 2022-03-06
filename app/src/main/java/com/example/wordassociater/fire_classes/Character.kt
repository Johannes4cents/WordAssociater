package com.example.wordassociater.fire_classes

import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.utils.LiveClass
import com.google.firebase.firestore.Exclude

data class Character(
        override var id: Long = 0,
        override var name: String = "",
        override var connectId: Long = 0,
        override var imgUrl : String = "",
        override var importance: SnippetPart.Importance = SnippetPart.Importance.Main,
        ): SnippetPart, LiveClass {
        override var description: String = ""

        override var storyLineList: MutableList<Long> = mutableListOf()
        override var snippetsList: MutableList<Long> = mutableListOf()
        override var eventList: MutableList<Long> = mutableListOf()
        override var wordList: MutableList<Long> = mutableListOf()
        override var image: Long = 21

        @get:Exclude
        override val liveSnippets = MutableLiveData<List<LiveClass>>()
        @get:Exclude
        override val liveWords = MutableLiveData<List<LiveClass>>()
        @get:Exclude
        override val liveEvents = MutableLiveData<List<LiveClass>>()
        @get:Exclude
        override val liveStoryLines = MutableLiveData<List<LiveClass>>()
        @get:Exclude
        override val liveWordsSearch = MutableLiveData<List<Word>>()
        @get:Exclude
        override val liveStoryLinesOnly = MutableLiveData<List<StoryLine>>()
        @Exclude
        override var oldSnippetPart : SnippetPart? = null
        @Exclude
        override var selected = false
        override var sortingOrder: Int = id.toInt()

        @Exclude
        override var isAHeader = false

        var isLeft = true


        companion object {
                val any = Character(id= 0, name="Any", imgUrl = "")
                fun getIdList(charList: List<Character>): MutableList<Long> {
                        val idList = mutableListOf<Long>()
                        for(c in charList) {
                                idList.add(c.id)
                        }
                        return idList
                }

        }

        override fun delete() {
                for(s in snippetsList) {
                        val snippet = Main.getSnippet(s)
                        if(snippet != null) {
                                snippet.characterList.remove(this.id)
                                FireSnippets.update(snippet.id, "characterList", snippet.characterList)
                        }
                }

                for(e in eventList) {
                        val event = Main.getEvent(e)
                        if(event != null) {
                                event.characterList.remove(this.id)
                                FireChars.update(event.id, "characterList", event.characterList)
                        }
                }

                val word = Main.wordsList.value!!.find { w -> w.connectId == connectId }
                word?.delete()
                FireChars.delete(this.id)
        }



        @Exclude
        override fun copyMe(): Character {
                val newCharacter = Character()
                newCharacter.storyLineList = storyLineList.toMutableList()
                newCharacter.eventList = eventList.toMutableList()
                newCharacter.id = 999999999999
                newCharacter.description = description
                newCharacter.wordList = wordList.toMutableList()
                newCharacter.snippetsList = snippetsList.toMutableList()
                newCharacter.importance = importance
                newCharacter.connectId = connectId
                oldSnippetPart = newCharacter
                return newCharacter
        }
}

