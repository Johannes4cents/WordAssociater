package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main

data class Character(
        val name: String = "", val imgUrl : String = "",
        val strainsList: MutableList<Strain> = mutableListOf(),
        val snippetsList: MutableList<Snippet> = mutableListOf(),
        var id: Long = 0,
        var connectId: Long = 0,
        var importance: Importance = Importance.Side) {
        var selected = false
        var isHeader = false
        enum class Importance(val text: String) {Main("Main"), Side("Side"), Minor("Minor"), Mentioned("Mentioned")}


        companion object {
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

