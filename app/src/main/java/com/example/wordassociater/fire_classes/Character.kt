package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireChars
import com.google.firebase.firestore.Exclude

data class Character(
        val name: String = "", val imgUrl : String = "",
        val snippetsList: MutableList<Long> = mutableListOf(),
        val eventList: MutableList<Long> = mutableListOf(),
        val proseList: MutableList<Long> = mutableListOf(),
        var id: Long = 0,
        var connectId: Long = 0,
        var importance: Importance = Importance.Side,
        var dialogueList: MutableList<Long> = mutableListOf()) {
        @Exclude
        var selected = false
        @Exclude
        var isHeader = false

        var isLeft = true
        enum class Importance(val text: String) {Main("Main"), Side("Side"), Minor("Minor"), Mentioned("Mentioned")}

        @Exclude
        fun getEvents(): List<Event> {
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
        fun getProse(): List<Prose> {
                val list = mutableListOf<Prose>()
                val toRemoveList = mutableListOf<Long>()
                for(id in eventList) {
                        val prose = Main.getProse(id)
                        if(prose != null) list.add(prose)
                        else toRemoveList.add(id)
                }

                for(id in toRemoveList) {
                        proseList.remove(id)
                        FireChars.update(this.id, "proseList", proseList)
                }
                return list
        }


        companion object {
                val any = Character(id= 22, name="Any")
                fun getIdList(charList: List<Character>): MutableList<Long> {
                        val idList = mutableListOf<Long>()
                        for(c in charList) {
                                idList.add(c.id)
                        }
                        return idList
                }

                fun getCharactersById(idList : List<Long>): List<Character> {
                        val charList = mutableListOf<Character>()
                        for(long in idList) {
                                val char = Main.getCharacter(long)
                                if(char != null) charList.add(char)
                        }
                        return charList
                }
        }
}

