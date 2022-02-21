package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.google.firebase.firestore.Exclude

data class Dialogue(
        var id: Long = 0,
        var charList: MutableList<Long> = mutableListOf(),
        var content: MutableList<Long> = mutableListOf()
) {
    @Exclude
    fun getCharacter(): List<Character> {
        val characterList = mutableListOf<Character>()
        for(c in charList) {
            var char = Main.getCharacter(c)
            if(char != null) characterList.add(char)
        }
        return characterList
    }
}